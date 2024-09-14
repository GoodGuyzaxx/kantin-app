package uningrat.kantin.ui.user.updateprofile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import uningrat.kantin.data.pref.UserModel
import uningrat.kantin.data.retrofit.response.UpdateProfileResponse
import uningrat.kantin.repository.KantinRepository

class UpdateProfileViewModel(private val repository: KantinRepository): ViewModel() {

    private val _updateProfileResponse = MutableLiveData<UpdateProfileResponse>()
    val updateProfileResponse: LiveData<UpdateProfileResponse> = _updateProfileResponse

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun updateProfile (id : String, email : String, noTelp: String ){
        viewModelScope.launch {
            try {
                val response = repository.updateProfile(id, email, noTelp)
                _updateProfileResponse.postValue(response)
            }catch (e : HttpException) {
                val jsonString = e.response()?.errorBody()?.toString()
                val errorBody = Gson().fromJson(jsonString, UpdateProfileResponse::class.java)
                val errorMessage = errorBody
                _updateProfileResponse.postValue(errorMessage)
                Log.d("TAG", "updateProfile: $errorMessage")

            }
        }
    }

}