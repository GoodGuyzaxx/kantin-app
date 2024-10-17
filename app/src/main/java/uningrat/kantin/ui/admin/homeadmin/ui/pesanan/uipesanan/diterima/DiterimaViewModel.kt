package uningrat.kantin.ui.admin.homeadmin.ui.pesanan.uipesanan.diterima

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
import java.io.IOException

class DiterimaViewModel(private val repository: KantinRepository): ViewModel() {

    private val _responseTransaksi = MutableLiveData<ListTransaksiResponse>()
    val responseTransaksi: LiveData<ListTransaksiResponse> =_responseTransaksi


    fun getDataTransaksiByStatus(id: String,status: String) {
        viewModelScope.launch {
            try {
                val response = repository.getDataTransaksiByStatus(id, status)
                _responseTransaksi.postValue(response) // Success case
            } catch (e: HttpException) {
                // Handle HttpException (e.g., 404, 500, etc.)
                withContext(Dispatchers.Main) {
                    handleHttpException(e)
                }
            } catch (e: IOException) {
                // Handle network-related errors (e.g., no internet, timeout)
                withContext(Dispatchers.Main) {
                    Log.e("TAG", "Network error: ${e.localizedMessage}")
                }
            } catch (e: Exception) {
                // Handle any other types of exceptions
                withContext(Dispatchers.Main) {
                    Log.e("TAG", "Unexpected error: ${e.localizedMessage}")
                }
            }
        }
    }

    fun updateDataTransaksi(id: String, status: String){
        viewModelScope.launch {
            val response = repository.updateDataTransaksi(id,status)
            try {
                _responseTransaksi.postValue(response)
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

    fun getKantinSession(): LiveData<UserModel> = repository.getSession().asLiveData()

    private fun handleHttpException(e: HttpException) {
        when (e.code()) {
            404 -> {
                // Handle 404 Not Found
                val jsonString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, ListTransaksiResponse::class.java)
                val errorMessage = errorBody?.message ?: "Resource not found"
                _responseTransaksi.postValue(null) // Handle empty data case
                Log.e("TAG", "HTTP 404 Error: $errorMessage")

            }
            500 -> {
                // Handle 500 Internal Server Error
                Log.e("TAG", "HTTP 500 Error: Server is down.")

            }
            else -> {
                // Handle other HTTP errors
                val errorMessage = e.message ?: "Something went wrong"
                Log.e("TAG", "HTTP Error ${e.code()}: $errorMessage")
            }
        }
    }
}