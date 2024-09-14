package uningrat.kantin.ui.user.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import uningrat.kantin.data.pref.UserModel
import uningrat.kantin.data.retrofit.response.LoginResponse
import uningrat.kantin.repository.KantinRepository

class LoginViewModel(private val repository: KantinRepository):  ViewModel()
{
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _toastEvent = Channel<String>(Channel.BUFFERED)
    val toastEvent = _toastEvent.receiveAsFlow()

    fun getLogin(email:String, password:String){
        viewModelScope.launch {
            try {
                val response = repository.postLogin(email,password)
                if (response.success){
                    saveSession(
                        UserModel(
                            response.data.idKonsumen,
                            response.data.namaKonsumen,
                            response.data.email,
                            response.data.noTelp,
                            response.status,
                            true
                        )
                    )
                }
                _loginResponse.postValue(response)
            }catch (e: HttpException){
                val jsonString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, LoginResponse::class.java)
                val errorMessage = errorBody.message
                _loginResponse.postValue(errorBody)
                Log.d("TAG", "getLogin: $errorMessage")
            }catch (e : Exception){
                withContext(Dispatchers.Main){
                    Log.d("TAG", "getLogin: ${e}")
                    _toastEvent.send("Periksa Koneksi Internet Anda")
                }
            }
        }
    }

    private fun saveSession(user: UserModel){
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun getSession(): LiveData<UserModel>  {
        return repository.getSession().asLiveData()
    }
}