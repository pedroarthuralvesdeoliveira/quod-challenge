package br.com.fiap.quod.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
fun Auth(authScreenViewModel: AuthScreenViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    val cpf by authScreenViewModel
        .cpfState
        .observeAsState(initial = "")

    val nome by authScreenViewModel
        .nomeState
        .observeAsState(initial = "")

    val endereco by authScreenViewModel
        .enderecoState
        .observeAsState(initial = "")

    val celular by authScreenViewModel
        .celularState
        .observeAsState(initial = "")

    val inputValido = authScreenViewModel.validar()

    Column {
        Row {
            CaixaDeEntrada(
                shape = RoundedCornerShape(32.dp),
                value = cpf,
                maxLines = 5,
                placeholder = "013.666.444-75",
                label = "Digite o CPF",
                color = Color.Gray.copy(0.5f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardType = KeyboardType.Text
            ) {
                authScreenViewModel.onCpfChanged(it)
            }
        }

        Row {
            CaixaDeEntrada(
                shape = RoundedCornerShape(32.dp),
                value = nome,
                maxLines = 5,
                placeholder = "Neymar Junior",
                label = "Digite o nome",
                color = Color.Gray.copy(0.5f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardType = KeyboardType.Text
            ) {
                authScreenViewModel.onNomeChanged(it)
            }
        }

        Row {
            CaixaDeEntrada(
                shape = RoundedCornerShape(32.dp),
                value = endereco,
                maxLines = 5,
                placeholder = "Rua Engenheiro Rebouças nº 475",
                label = "Digite o endereço",
                color = Color.Gray.copy(0.5f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardType = KeyboardType.Text
            ) {
                authScreenViewModel.onEnderecoChanged(it)
            }
        }

        Row {
            CaixaDeEntrada(
                shape = RoundedCornerShape(32.dp),
                value = celular,
                maxLines = 5,
                placeholder = "45991057977",
                label = "Digite o telefone",
                color = Color.Gray.copy(0.5f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardType = KeyboardType.Text
            ) {
                authScreenViewModel.onCelularChanged(it)
            }
        }

        Button(
            onClick = {
                if (inputValido) {
                    dialogMessage = "Perfil aceito com sucesso!"
                    showDialog = true
                } else {
                    dialogMessage = "Dados não conferem!"
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
                title = { Text("Permissão ao app") },
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
fun AuthPreview() {
    Auth(authScreenViewModel = AuthScreenViewModel())
}