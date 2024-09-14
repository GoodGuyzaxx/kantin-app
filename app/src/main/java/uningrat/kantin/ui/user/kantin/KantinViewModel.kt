package uningrat.kantin.ui.user.kantin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uningrat.kantin.data.pref.KantinModel
import uningrat.kantin.data.pref.UserModel
import uningrat.kantin.data.retrofit.response.MenuKantinResponse
import uningrat.kantin.repository.KantinRepository

class KantinViewModel(private val repository: KantinRepository): ViewModel() {

    private val _menuKantinResponse = MutableLiveData<MenuKantinResponse>()
    val menuKantinActivity: LiveData<MenuKantinResponse> = _menuKantinResponse

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun saveKantinSession(kantin: KantinModel){
        viewModelScope.launch {
            repository.saveKantinSession(kantin)
        }
    }

//    fun getKaninById(id : String){
//        val client = ApiConfig.getApiService().getMenuByKantinId(id)
//        client.enqueue(object : Callback<MenuKantinResponse> {
//            override fun onResponse(p0: Call<MenuKantinResponse>, p1: Response<MenuKantinResponse>) {
//                if (p1.isSuccessful){
//                    _menuKantinResponse.value = p1.body()
//                }else {
//                    Log.e("TAG", "onResponse: ${p1.message()}", )
//                }
//            }
//
//            override fun onFailure(p0: Call<MenuKantinResponse>, p1: Throwable) {
//                Log.e("TAG", "onFailure: ${p1.message.toString()}", )
//            }
//
//        })
//    }
}