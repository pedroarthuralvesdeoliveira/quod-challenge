package br.com.fiap.quod.biometria.digital

import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class BiometricHelper(private val activity: FragmentActivity) {
    private val executor = ContextCompat.getMainExecutor(activity)

    fun createBiometricPrompt(callback: BiometricPrompt.AuthenticationCallback): BiometricPrompt {
        return BiometricPrompt(activity, executor, callback)
    }
}