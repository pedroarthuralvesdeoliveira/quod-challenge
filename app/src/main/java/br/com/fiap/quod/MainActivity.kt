package br.com.fiap.quod

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.quod.biometria.digital.BiometricHelper
import br.com.fiap.quod.menu.Menu

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val biometricHelper = BiometricHelper(this)
            Menu(biometricHelper)
        }
    }
}