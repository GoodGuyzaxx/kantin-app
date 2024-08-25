package uningrat.kantin.ui.user.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import uningrat.kantin.data.retrofit.response.LoginResponse
import uningrat.kantin.data.retrofit.response.RegisterResponse
import uningrat.kantin.repository.KantinRepository

class RegisterViewModel(private val repository: KantinRepository): ViewModel() {

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private val _toastEvent = Channel<String>(Channel.BUFFERED)
    val toastEvent = _toastEvent.receiveAsFlow()

    fun getRegister(nama: String, email: String, noTelp: String, password: String){
        viewModelScope.launch {
            try {
            val response = repository.postRegister(nama, email, noTelp, password)
            _registerResponse.postValue(response)
            }catch (e: HttpException){
                val jsonString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, RegisterResponse::class.java)
                val errorMessage = errorBody.message
                _registerResponse.postValue(errorBody)
                Log.d("TAG", "Register: $errorMessage")
            }catch (e : Exception){
                withContext(Dispatchers.Main){
                    Log.d("TAG", "Register: ${e}")
                    _toastEvent.send("Periksa Jarignan Anda")
                }
            }
        }
    }
}