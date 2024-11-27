package br.com.fiap.quod.biometria.facial

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.fiap.quod.biometria.digital.BiometricHelper
import br.com.fiap.quod.R

@Composable
fun BiometricFingerprintAuthentication(
    biometricHelper: BiometricHelper,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val context = LocalContext.current
    val biometricManager = BiometricManager.from(context)

    val canAuthenticateFingerprint = biometricManager.canAuthenticate(
        BiometricManager.Authenticators.BIOMETRIC_STRONG
    ) == BiometricManager.BIOMETRIC_SUCCESS

    Button(
        onClick = {
            if (canAuthenticateFingerprint) {
                try {
                    val promptInfo = BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Autenticação por Impressão Digital")
                        .setSubtitle("Use sua impressão digital para autenticar")
                        .setNegativeButtonText("Cancelar")
                        .build()

                    val biometricPrompt = biometricHelper.createBiometricPrompt(
                        object : BiometricPrompt.AuthenticationCallback() {
                            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                onSuccess()
                            }

                            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                                onError(errString.toString())
                            }
                        }
                    )

                    biometricPrompt.authenticate(promptInfo)
                } catch (e: Exception) {
                    onError("Erro ao iniciar autenticação por impressão digital: ${e.message}")
                }
            } else {
                onError("Autenticação por impressão digital não suportada neste dispositivo")
            }
        },
        enabled = canAuthenticateFingerprint
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_fingerprint_24),
                contentDescription = "Ícone de impressão digital"
            )
            Text("Autenticar com Impressão Digital")
        }
    }
}

@Composable
fun BiometricFaceAuthentication(
    biometricHelper: BiometricHelper,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val context = LocalContext.current
    val biometricManager = BiometricManager.from(context)

    val canAuthenticateFace = biometricManager.canAuthenticate(
        BiometricManager.Authenticators.BIOMETRIC_WEAK
    ) == BiometricManager.BIOMETRIC_SUCCESS

    Button(
        onClick = {
            if (canAuthenticateFace) {
                try {
                    val promptInfo = BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Autenticação por Reconhecimento Facial")
                        .setSubtitle("Use seu rosto para autenticar")
                        .setNegativeButtonText("Cancelar")
                        .build()

                    val biometricPrompt = biometricHelper.createBiometricPrompt(
                        object : BiometricPrompt.AuthenticationCallback() {
                            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                onSuccess()
                            }

                            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                                onError(errString.toString())
                            }
                        }
                    )

                    biometricPrompt.authenticate(promptInfo)
                } catch (e: Exception) {
                    onError("Erro ao iniciar autenticação por reconhecimento facial: ${e.message}")
                }
            } else {
                onError("Autenticação por reconhecimento facial não suportada neste dispositivo")
            }
        },
        enabled = canAuthenticateFace
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