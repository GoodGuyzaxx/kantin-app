package uningrat.kantin.ui.user.Home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import uningrat.kantin.data.pref.UserModel
import uningrat.kantin.data.retrofit.ApiConfig
import uningrat.kantin.data.retrofit.response.DataItem
import uningrat.kantin.data.retrofit.response.KantinResponse
import uningrat.kantin.data.retrofit.response.RekomendasiResponse
import uningrat.kantin.repository.KantinRepository

class HomeViewModel(private val repository: KantinRepository): ViewModel() {

//    private val session = repository.getSession().asLiveData()
//
//    val userName: LiveData<String> = session.map { it?.nama_konsumen ?: "Guest" }

    private val _kantinResponse = MutableLiveData<List<DataItem>>()
    val kantinResponse: LiveData<List<DataItem>> = _kantinResponse

    private val _rekomendasiResponse = MutableLiveData<RekomendasiResponse>()
    val rekomendasiResponse: LiveData<RekomendasiResponse> = _rekomendasiResponse

    init {
        getKantin()
        getRekomendasiMenu()
    }

    fun getRekomendasiMenu(){
        viewModelScope.launch {
            try {
                val response = repository.getRekomendasiMenu()
                _rekomendasiResponse.postValue(response)
            }catch (e : HttpException){
                val jsonString = e.response()?.body()?.toString()
                val errorBody = Gson().fromJson(jsonString, RekomendasiResponse::class.java)
                val errorMessage = errorBody.message
                _rekomendasiResponse.postValue(errorBody)
                Log.d("TAG", "addMenu: $errorMessage")
            }
        }
    }


    fun getKantin(){
        val client = ApiConfig.getApiService().getListKantin()
        client.enqueue(object : Callback<KantinResponse>{
            override fun onResponse(p0: Call<KantinResponse>, p1: Response<KantinResponse>) {
                if (p1.isSuccessful){
                    _kantinResponse.value = p1.body()?.data
                }else {
                    Log.e("TAG", "onResponse: ${p1.message()}", )
                }
            }

            override fun onFailure(p0: Call<KantinResponse>, p1: Throwable) {
                Log.e("TAG", "onFailure: ${p1.message.toString()}", )
            }

        })
    }
    fun getSession(): LiveData<UserModel>  {
        return repository.getSession().asLiveData()
    }
}