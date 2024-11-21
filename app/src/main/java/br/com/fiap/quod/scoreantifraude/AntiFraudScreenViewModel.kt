package br.com.fiap.quod.scoreantifraude

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class AntiFraudScreenViewModel : ViewModel() {
    private val _cpf = MutableLiveData<String>()
    val cpfState: LiveData<String> = _cpf

    fun onCpfChanged(novoCpf: String) {
        _cpf.value = novoCpf
    }

    fun validarScore(): Int {
        val randomNumber = Random.nextInt(0, 1001)
        return randomNumber
    }
}