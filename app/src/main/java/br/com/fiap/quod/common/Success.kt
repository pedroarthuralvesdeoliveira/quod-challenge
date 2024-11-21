package br.com.fiap.quod.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SuccessMessage(message: String) {
    Text(
        text = "Sucesso: $message",
        color = Color.Green,
        modifier = Modifier.padding(8.dp)
    )
}