package uningrat.kantin.ui.admin.editmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import uningrat.kantin.data.pref.UserModel
import uningrat.kantin.data.retrofit.response.AddMenuResponse
import uningrat.kantin.repository.KantinRepository

class EditMenuViewModel(private val repository: KantinRepository): ViewModel() {


    private val _EditResponse = MutableLiveData<AddMenuResponse>()
    val EditResponse: LiveData<AddMenuResponse> = _EditResponse

    fun editMenu(
        id: String,
        idKantin : RequestBody,
        namaMenu: RequestBody,
        deskripsi : RequestBody,
        harga: RequestBody,
        stock : RequestBody,
        kategori : RequestBody,
        multipartBody: MultipartBody.Part,
        _method : RequestBody
    ) {
        viewModelScope.launch {
            try {
                val response = repository.editMenu(id, idKantin, namaMenu, deskripsi, harga, stock, kategori, multipartBody, _method)
                _EditResponse.postValue(response)
            }catch (e : HttpException) {

            }
        }
    }

    fun getSession() : LiveData<UserModel> = repository.getSession().asLiveData()
}