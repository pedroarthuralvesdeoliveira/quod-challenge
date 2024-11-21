package br.com.fiap.quod.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun CaixaDeEntrada(
    shape: Shape,
    value: String,
    placeholder: String,
    label: String,
    color: Color,
    modifier: Modifier,
    maxLines : Int?,
    keyboardType: KeyboardType,
    atualizarValor: (String) -> Unit
)
{
    OutlinedTextField(
        maxLines = if (maxLines != null) maxLines else 1,
        shape = shape,
        value = value,
        onValueChange = {
            atualizarValor(it)
        },
        modifier = modifier,
        placeholder = {
            Text(text = placeholder)
        },
        label = {
            Text(text = label, color = color)
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}