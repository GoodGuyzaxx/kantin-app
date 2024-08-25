package uningrat.kantin.ui.user.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uningrat.kantin.data.pref.UserModel
import uningrat.kantin.data.retrofit.ApiConfig
import uningrat.kantin.data.retrofit.response.MenuItem
import uningrat.kantin.data.retrofit.response.MenuResponse
import uningrat.kantin.repository.KantinRepository

class MenuViewModel: ViewModel() {

    private val _listMakanan = MutableLiveData<List<MenuItem>>()
    val listMakanan: LiveData<List<MenuItem>> = _listMakanan

    private val _listMinuman = MutableLiveData<List<MenuItem>>()
    val listMinuman: LiveData<List<MenuItem>> = _listMinuman


    fun getMenuMakanan(id : String) {
        val client = ApiConfig.getApiService().getMenuMakanan(id)
        client.enqueue(object : Callback<MenuResponse>{
            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                if (response.isSuccessful){
                    _listMakanan.value = response.body()?.data
                }else {
                    Log.e("TAG", "onResponse: ${response.message()}", )
                }
            }
            override fun onFailure(p0: Call<MenuResponse>, p1: Throwable) {
                Log.e("TAG", "onFailure: ${p1.message.toString()}", )
            }
        })
    }


    fun getMenuMinuman(id : String) {
        val client = ApiConfig.getApiService().getMenuMinuman(id)
        client.enqueue(object : Callback<MenuResponse>{
            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                if (response.isSuccessful){
                    _listMinuman.value = response.body()?.data
                }else {
                    Log.e("TAG", "onResponse: ${response.message()}", )
                }
            }
            override fun onFailure(p0: Call<MenuResponse>, p1: Throwable) {
                Log.e("TAG", "onFailure: ${p1.message.toString()}", )
            }
        })
    }


}

