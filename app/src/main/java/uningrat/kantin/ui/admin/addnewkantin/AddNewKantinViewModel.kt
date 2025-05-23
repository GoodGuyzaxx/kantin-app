package uningrat.kantin.ui.admin.addnewkantin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import uningrat.kantin.data.retrofit.response.AddNewKantinResponse
import uningrat.kantin.repository.KantinRepository

class AddNewKantinViewModel(private val repository: KantinRepository): ViewModel() {

    private val _registerAdminRespose =  MutableLiveData<AddNewKantinResponse>()
    val registerAdminResponse: LiveData<AddNewKantinResponse> = _registerAdminRespose

    fun postRegisterAdminKantin(namaKantin: String, idKantin: Int){
        viewModelScope.launch {
            try {
                val response = repository.postRegisterAdminKantin(namaKantin, idKantin)
                _registerAdminRespose.postValue(response)
            }catch (e : HttpException){
                val jsonString = e.response()?.errorBody().toString()
                val errorBody = Gson().fromJson(jsonString, AddNewKantinResponse::class.java)
                _registerAdminRespose.postValue(errorBody)
            }catch (e : Exception){
                Log.e("TAG", "postRegisterAdminKantin: $e", )
            }
        }
    }
}