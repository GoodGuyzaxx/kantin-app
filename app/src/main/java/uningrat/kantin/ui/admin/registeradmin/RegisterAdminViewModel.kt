package uningrat.kantin.ui.admin.registeradmin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import uningrat.kantin.data.retrofit.response.RegisterAdminResponse
import uningrat.kantin.repository.KantinRepository

class RegisterAdminViewModel(private val repository: KantinRepository): ViewModel() {

    private val _registerResponse = MutableLiveData<RegisterAdminResponse>()
    val registerResponse: LiveData<RegisterAdminResponse> = _registerResponse

    fun registerAdmin(nama_admin: String, email: String, noTelp: String,password: String){
        viewModelScope.launch {
            try {
                val response = repository.postAdminRegister(nama_admin, email, noTelp, password)
                _registerResponse.postValue(response)
            } catch (e : HttpException) {
                val jsonString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, RegisterAdminResponse::class.java)
//                val errorMessage = errorBody.message
                _registerResponse.postValue(errorBody)
            }
        }
    }
}