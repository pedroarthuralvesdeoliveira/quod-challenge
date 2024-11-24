package br.com.fiap.quod.biometria.digital

import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun BiometricAuthenticationButton(
    biometricHelper: BiometricHelper,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val biometricManager = BiometricManager.from(context)
    val canAuthenticate = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS

    Button(
        onClick = {
            if (canAuthenticate) {
                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Autenticação Biométrica")
                    .setSubtitle("Use sua impressão digital para autenticar")
                    .setNegativeButtonText("Cancelar")
                    .build()

                val biometricPrompt = biometricHelper.createBiometricPrompt(
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(
                            result: BiometricPrompt.AuthenticationResult
                        ) {
                            super.onAuthenticationSucceeded(result)
                            onClick()
                            Log.d("BiometricAuthentication", "Parabéns, você foi autenticado com sucesso!")
                        }

                        override fun onAuthenticationError(
                            errorCode: Int,
                            errString: CharSequence
                        ) {
                            super.onAuthenticationError(errorCode, errString)
                            Log.d("BiometricAuthentication", "Erro de autenticação: $errString")
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            Log.d("BiometricAuthentication", "Falha na autenticação")
                        }
                    }
                )

                biometricPrompt.authenticate(promptInfo)
            } else {
                Log.d("BiometricAuthentication", "Biometria não suportada")
            }
        },
        enabled = canAuthenticate
    ) {
        Text("Autenticar")
    }
}