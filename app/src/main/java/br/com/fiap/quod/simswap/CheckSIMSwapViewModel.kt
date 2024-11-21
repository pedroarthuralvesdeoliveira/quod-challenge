package br.com.fiap.quod.simswap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class CheckSIMSwapViewModel : ViewModel() {
    private val _numero = MutableLiveData<String>()
    val numeroState: LiveData<String> = _numero

    fun onNumeroChanged(novoNumero: String) {
        _numero.value = novoNumero
    }

    fun validarInput(): Boolean {
        val randomNumber = Random.nextInt(0, 101)
        return randomNumber % 2 == 0
    }
}