package uningrat.kantin.ui.admin.loginadmin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import uningrat.kantin.data.pref.UserModel
import uningrat.kantin.data.retrofit.response.LoginAdminResponse
import uningrat.kantin.repository.KantinRepository

class LoginAdminViewModel(private val repository: KantinRepository): ViewModel() {

    private val _adminResponse = MutableLiveData<LoginAdminResponse>()
    val adminResponse: LiveData<LoginAdminResponse> = _adminResponse

    fun adminPostLogin(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.postAdminLogin(email, password)
                if (response.success){
                    val convId = response.data.id.toString()
                    saveSession(
                        UserModel(
                            convId,
                            response.data.namaAdmin,
                            response.data.email,
                            response.data.noTelp,
                            response.status,
                            true
                        )
                    )
                }
                _adminResponse.postValue(response)
            } catch (e: HttpException) {
                val jsonString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, LoginAdminResponse::class.java)
                val errorMessage = errorBody.message
                _adminResponse.postValue(errorBody)
                Log.e("TAG", "adminPostLogin: $errorMessage",)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("TAG", "getLogin: ${e}")
                }
            }
        }
    }

    private fun saveSession(user: UserModel){
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}