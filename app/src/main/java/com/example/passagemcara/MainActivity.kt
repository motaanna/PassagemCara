package com.example.passagemcara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passagemcara.ui.theme.PassagemCaraTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PassagemCaraTheme {
                AppCalculadorDeGorjeta()

     }
        }
    }
}

@Preview
@Composable
fun AppCalculadorDeGorjeta(){
    var passagensPorDia  by remember { mutableStateOf("") }
    var qtdDeDia by remember { mutableStateOf("") }


    val gerenciadorFocus = LocalFocusManager.current

    var dias =
        Calculardias(
            passagensPorDia, qtdDeDia) // chama a função para calcular o arredondamento, recebe o valor e guarda na variavel

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        Column( // estrutura o conteúdo em coluna
            verticalArrangement = Arrangement.Top, // coloca o conteudo no topo da tela
            horizontalAlignment = Alignment.CenterHorizontally, // coloca o conteudo da coluna alinhado no centro horizontal
            modifier = Modifier
                .padding(30.dp)
        ) {
            Text(
                text ="Gastos Com Passagens",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.size(20.dp))

            viagem(
                value = passagensPorDia ,
                label = "Passagens por dia ",
                onValueChange = {passagensPorDia  = it},
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(
                    onNext = {gerenciadorFocus.moveFocus(FocusDirection.Next)}
                )
            )

            viagem(
                value = qtdDeDia,
                label = "Quantos dias de Viagens  ",
                onValueChange = {qtdDeDia = it},
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onNext = {gerenciadorFocus.clearFocus()}
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold
                )


            }

            Spacer(modifier = Modifier.size(20.dp))

            Text(
                text =" Valor por mês ${NumberFormat.getCurrencyInstance().format(dias)}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )


        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun viagem(
    value:String,
    label:String,
    onValueChange:(String)->Unit,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions
){
    TextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        label = { Text(text = label)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        textStyle = TextStyle(color = Color.Black),
}
@Composable
fun Calculardias(
    dias: String,
    passagens: String
):Double{

    var valor = (dias.toDoubleOrNull()?:0.0) * (passagens.toDoubleOrNull()?:0.0)

    valor = kotlin.math.ceil(valor)

    return valor

}
