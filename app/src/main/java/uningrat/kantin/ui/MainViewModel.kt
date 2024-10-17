package uningrat.kantin.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import uningrat.kantin.repository.KantinRepository

class MainViewModel(private val repository: KantinRepository): ViewModel() {

    fun getSession() = repository.getSession().asLiveData()
}