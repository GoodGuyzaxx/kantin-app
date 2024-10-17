package uningrat.kantin.ui.user.transaksi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import uningrat.kantin.data.pref.UserModel
import uningrat.kantin.data.retrofit.response.ListTransaksiResponse
import uningrat.kantin.repository.KantinRepository

class TransaksiViewModel (private val repository: KantinRepository): ViewModel() {

    private val _responseTransaksi = MutableLiveData<ListTransaksiResponse>()
    val responseTransaksi: LiveData<ListTransaksiResponse> =_responseTransaksi


    fun getDataTransaksi(id : String){
        viewModelScope.launch {
            try {
            val response = repository.getDataTransaksi(id)
            _responseTransaksi.value = response
            }catch (e : HttpException){
                val jsonString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, ListTransaksiResponse::class.java)
                val errorMessage = errorBody.message
                _responseTransaksi.postValue(errorBody)
                Log.d("TAG", "getLogin: $errorMessage")
            }catch (e : Exception){
                withContext(Dispatchers.Main){
                    Log.d("TAG", "getLogin: ${e}")
                }
            }
        }
    }

    fun getSession(): LiveData<UserModel> = repository.getSession().asLiveData()

}