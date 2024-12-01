package br.com.fiap.quod.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay

@Composable
fun CustomAlertDialog(
    showDialog: Boolean,
    isSuccess: Boolean,
    message: String,
    onDismiss: () -> Unit,
    //navController: NavController
) {
    rememberCoroutineScope()

    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss
        ) {
            Card {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isSuccess) "Sucesso" else "Erro",
                        style = MaterialTheme.typography.headlineLarge,
                        color = if (isSuccess) Color(0xff7ca37c) else Color.Red
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = message
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    LaunchedEffect(key1 = showDialog) {
                        if (showDialog) {
                            delay(3000)
                            //navController.navigate("home") // Redirecionar para a tela inicial
                            onDismiss() // Fechar o diálogo
                        }
                    }

                    Button(
                        onClick = {
                            //navController.navigate("home")
                            onDismiss()
                        }
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}