package uningrat.kantin.ui.admin.homeadmin.ui.menuadmin

import android.util.Log.e
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import uningrat.kantin.data.pref.UserModel
import uningrat.kantin.data.retrofit.ApiConfig
import uningrat.kantin.data.retrofit.response.MenuResponse
import uningrat.kantin.repository.KantinRepository

class MenuAdminViewModel(private val repository: KantinRepository): ViewModel() {

    private val _responseMenu = MutableLiveData<MenuResponse>()
    val responseMenu : LiveData<MenuResponse> = _responseMenu

    fun getAllMenuByKantin(id : String){
        viewModelScope.launch {
            try {
                val response = repository.gaetAllMenuByKantin(id)
                _responseMenu.postValue(response)
            }catch (e: HttpException){

            }
        }
    }

//    private val _responseMenu = MutableLiveData<MenuResponse>()
    fun deleteMenu(id: String) {
        val client = ApiConfig.getApiService().deleteMenuByKantinId(id)
        client.enqueue(object : Callback<MenuResponse> {
            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                if (response.isSuccessful){
                    _responseMenu.value = response.body()
                }else {
                    e("TAG", "onResponse: ${response.message()}", )
                }
            }
            override fun onFailure(p0: Call<MenuResponse>, p1: Throwable) {
                e("TAG", "onFailure: ${p1.message.toString()}", )
            }
        })
    }

    fun getSession(): LiveData<UserModel> = repository.getSession().asLiveData()

}