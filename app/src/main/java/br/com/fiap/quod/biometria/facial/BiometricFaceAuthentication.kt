package br.com.fiap.quod.biometria.facial

import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.quod.biometria.digital.BiometricHelper

@Composable
fun BiometricFaceAuthentication(
    navController: NavController,
    biometricHelper: BiometricHelper,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val context = LocalContext.current
    val biometricManager = BiometricManager.from(context)

    val canAuthenticateFace = biometricManager.canAuthenticate(
        BiometricManager.Authenticators.BIOMETRIC_WEAK
    ) == BiometricManager.BIOMETRIC_SUCCESS

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
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

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    if (canAuthenticateFace) {
                        try {
                            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                                .setTitle("Autenticação por Reconhecimento Facial")
                                .setSubtitle("Use seu rosto para autenticar")
                                .setNegativeButtonText("Cancelar")
                                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK)
                                .build()

                            val biometricPrompt = biometricHelper.createBiometricPrompt(
                                object : BiometricPrompt.AuthenticationCallback() {
                                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                        super.onAuthenticationSucceeded(result)
                                        Log.d("BiometricAuth", "Autenticação por reconhecimento facial bem sucedida")
                                        onSuccess()
                                    }

                                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                                        super.onAuthenticationError(errorCode, errString)
                                        Log.e("BiometricAuth", "Erro de Autenticação por Impressão Digital: $errorCode - $errString")
                                        val errorMessage = when (errorCode) {
                                            BiometricPrompt.ERROR_HW_NOT_PRESENT ->
                                                "Hardware biométrico não encontrado"
                                            BiometricPrompt.ERROR_HW_UNAVAILABLE ->
                                                "Hardware biométrico indisponível"
                                            BiometricPrompt.ERROR_NO_BIOMETRICS ->
                                                "Nenhuma face cadastrada"
                                            BiometricPrompt.ERROR_LOCKOUT ->
                                                "Muitas tentativas. Tente novamente mais tarde"
                                            BiometricPrompt.ERROR_LOCKOUT_PERMANENT ->
                                                "Bloqueio permanente. Use outra forma de autenticação"
                                            else -> errString.toString()
                                        }
                                        onError(errorMessage)
                                    }

                                    override fun onAuthenticationFailed() {
                                        super.onAuthenticationFailed()
                                        Log.e("BiometricAuth", "Falha na Autenticação por reconhecimento facial")
                                        onError("Falha na autenticação por reconhecimento facial. Tente novamente.")
                                    }
                                }
                            )

                            biometricPrompt.authenticate(promptInfo)
                            Log.d("BiometricAuth", "Solicitação de autenticação por reconhecimento facial iniciada")
                        } catch (e: Exception) {
                            Log.e("BiometricAuth", "Erro ao iniciar autenticação por reconhecimento facial", e)
                            onError("Erro ao iniciar autenticação por reconhecimento facial: ${e.message}")
                        }
                    } else {
                        Log.e("BiometricAuth", "Autenticação por reconhecimento facial não suportada")
                        onError("Autenticação por reconhecimento facial não suportada neste dispositivo")
                    }
                },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = "Ícone de reconhecimento facial"
                    )
                    Text("Autenticar com Reconhecimento Facial")
                }
            }
        }
    }
}