package br.com.fiap.quod

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import br.com.fiap.quod.biometria.digital.BiometricAuthenticationButton
import br.com.fiap.quod.biometria.digital.BiometricHelper
import br.com.fiap.quod.ui.theme.QuodTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val biometricHelper = BiometricHelper(this)

        setContent {
            QuodTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BiometricAuthenticationButton(
                        biometricHelper = biometricHelper,
                        onClick = {}
                    )
                }
            }
        }
    }
}