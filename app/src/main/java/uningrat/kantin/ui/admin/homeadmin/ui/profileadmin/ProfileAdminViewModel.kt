package uningrat.kantin.ui.admin.homeadmin.ui.profileadmin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uningrat.kantin.data.pref.UserModel
import uningrat.kantin.repository.KantinRepository

class ProfileAdminViewModel(private val repository: KantinRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }


}