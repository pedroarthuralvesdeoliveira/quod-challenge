package br.com.fiap.quod.biometria.digital

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class BiometricHelper(private val activity: FragmentActivity) {
    private val executor = ContextCompat.getMainExecutor(activity)
    private val biometricManager = BiometricManager.from(activity)

    fun createBiometricPrompt(callback: BiometricPrompt.AuthenticationCallback): BiometricPrompt {
        return BiometricPrompt(activity, executor, callback)
    }

    fun checkBiometricCapabilities(): String {
        val authenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG or
                BiometricManager.Authenticators.BIOMETRIC_WEAK

        val result = biometricManager.canAuthenticate(authenticators)

        return "Status: ${getBiometricStatusString(result)}"
    }

    private fun getBiometricStatusString(status: Int): String {
        return when (status) {
            BiometricManager.BIOMETRIC_SUCCESS -> "Disponível"
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> "Hardware não encontrado"
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> "Hardware indisponível"
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> "Não cadastrado"
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED ->
                "Atualização de segurança necessária"
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> "Não suportado"
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> "Status desconhecido"
            else -> "Status não identificado: $status"
        }
    }
}