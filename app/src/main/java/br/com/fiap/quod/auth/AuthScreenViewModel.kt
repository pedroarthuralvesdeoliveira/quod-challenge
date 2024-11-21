package br.com.fiap.quod.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class AuthScreenViewModel : ViewModel() {
    private val _cpf = MutableLiveData<String>()
    val cpfState: LiveData<String> = _cpf

    fun onCpfChanged(novoCpf: String) {
        _cpf.value = novoCpf
    }

    private val _nome = MutableLiveData<String>()
    val nomeState: LiveData<String> = _nome

    fun onNomeChanged(novoNome: String) {
        _nome.value = novoNome
    }

    private val _endereco = MutableLiveData<String>()
    val enderecoState: LiveData<String> = _endereco

    fun onEnderecoChanged(novoEndereco: String) {
        _endereco.value = novoEndereco
    }

    private val _celular = MutableLiveData<String>()
    val celularState: LiveData<String> = _celular

    fun onCelularChanged(novoCelular: String) {
        _celular.value = novoCelular
    }

    fun validar(): Boolean {
        val randomNumber = Random.nextInt(0, 1001)
        return randomNumber % 2 == 0
    }
}