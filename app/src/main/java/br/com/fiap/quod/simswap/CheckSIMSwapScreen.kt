package br.com.fiap.quod.simswap

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.quod.components.CaixaDeEntrada
import br.com.fiap.quod.components.CustomAlertDialog

@Composable
fun CheckSIMSwap(
    navController: NavController,
    checkSIMSwapViewModel: CheckSIMSwapViewModel
) {
    val numero by checkSIMSwapViewModel
        .numeroState.observeAsState(initial = "")

    var showCustomDialog by remember { mutableStateOf(false) }
    var isCustomDialogSuccess by remember { mutableStateOf(false) }
    var customDialogMessage by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                border = BorderStroke(1.dp, Color.Black),
                shape = RoundedCornerShape(32.dp),
                onClick = {navController.navigate("home")},
                colors = ButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Cyan
                )
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "", tint = Color.LightGray)
            }
        }

        Column (
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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
                        customDialogMessage = "Tudo o.k. com o CHIP!"
                        isCustomDialogSuccess = true
                    } else {
                        customDialogMessage = "O chip foi trocado recentemente. "
                        isCustomDialogSuccess = false
                    }
                    showCustomDialog = true
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

            if (showCustomDialog) {
                CustomAlertDialog(
                    showDialog = showCustomDialog,
                    isSuccess = isCustomDialogSuccess,
                    message = customDialogMessage,
                    onDismiss = { showCustomDialog = false },
                    navController = navController
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CheckSIMSwapPreview() {
    val navController = rememberNavController()
    CheckSIMSwap(navController = navController, checkSIMSwapViewModel = CheckSIMSwapViewModel())
}