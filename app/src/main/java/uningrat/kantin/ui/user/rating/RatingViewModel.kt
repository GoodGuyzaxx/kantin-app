package uningrat.kantin.ui.user.rating

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import uningrat.kantin.data.pref.UserModel
import uningrat.kantin.repository.KantinRepository

class RatingViewModel (private val repository: KantinRepository): ViewModel() {

    fun getSession(): LiveData<UserModel> = repository.getSession().asLiveData()
}