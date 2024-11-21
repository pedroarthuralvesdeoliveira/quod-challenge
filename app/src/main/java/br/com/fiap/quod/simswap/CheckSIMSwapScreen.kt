package br.com.fiap.quod.simswap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fiap.quod.components.CaixaDeEntrada

@Composable
fun CheckSIMSwap(checkSIMSwapViewModel: CheckSIMSwapViewModel) {
    val numero by checkSIMSwapViewModel
        .numeroState.observeAsState(initial = "")

    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    Column (
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Informe o telefone com o código do pais e o DDD"
        )
        CaixaDeEntrada(
            shape = RoundedCornerShape(32.dp),
            value = numero,
            maxLines = 5,
            placeholder = "+5545991015545",
            label = "",
            color = Color.Gray.copy(0.5f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardType = KeyboardType.Text
        ) {
            checkSIMSwapViewModel.onNumeroChanged(it)
        }
        Button(
            onClick = {
                val inputValido = checkSIMSwapViewModel.validarInput()
                if (inputValido) {
                    dialogMessage = "Input enviado com sucesso!"
                    showDialog = true
                } else {
                    dialogMessage = "Erro no input. Verifique os dados."
                    showDialog = true
                }
            },
            modifier = Modifier
                .width(130.dp)
                .height(50.dp)
                .align(Alignment.CenterHorizontally)
                .padding(8.dp),
            colors = ButtonColors(
                contentColor = Color.White,
                containerColor = Color.Gray,
                disabledContainerColor = Color.Unspecified,
                disabledContentColor = Color.Unspecified
            )
        ) {
            Text(text = "Enviar")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Resultado") },
                text = { Text(dialogMessage) },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CheckSIMSwapPreview() {
    CheckSIMSwap(checkSIMSwapViewModel = CheckSIMSwapViewModel())
}