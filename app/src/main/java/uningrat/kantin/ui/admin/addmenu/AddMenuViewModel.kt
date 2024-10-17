package uningrat.kantin.ui.admin.addmenu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import uningrat.kantin.data.pref.UserModel
import uningrat.kantin.data.retrofit.response.AddMenuResponse
import uningrat.kantin.repository.KantinRepository

class AddMenuViewModel(private val repository: KantinRepository): ViewModel() {

    private val _addMenuResponse = MutableLiveData<AddMenuResponse>()
    val addMenuResponse: LiveData<AddMenuResponse> = _addMenuResponse

    fun addMenu(idKantin : RequestBody, namaMenu: RequestBody, deskripsi : RequestBody, harga: RequestBody, stock : RequestBody, kategori : RequestBody, multipartBody: MultipartBody.Part) {
        viewModelScope.launch {
        try {
            val response = repository.addMenu(idKantin, namaMenu, deskripsi, harga, stock, kategori, multipartBody)
            _addMenuResponse.postValue(response)
            }catch (e : HttpException) {
                val jsonString = e.response()?.body()?.toString()
                val errorBody = Gson().fromJson(jsonString,AddMenuResponse::class.java)
                val errorMessage = errorBody.message
                _addMenuResponse.postValue(errorBody)
                Log.d("TAG", "addMenu: $errorMessage")
            }
        }
    }

    fun getSession() : LiveData<UserModel> = repository.getSession().asLiveData()
}