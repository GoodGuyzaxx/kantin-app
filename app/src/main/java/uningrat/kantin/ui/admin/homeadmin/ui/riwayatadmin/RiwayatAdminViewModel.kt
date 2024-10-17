package uningrat.kantin.ui.admin.homeadmin.ui.riwayatadmin

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
import uningrat.kantin.data.retrofit.response.ListTransaksiResponse
import uningrat.kantin.data.retrofit.response.PenghasilaKantinResponse
import uningrat.kantin.repository.KantinRepository

class RiwayatAdminViewModel(private val repository: KantinRepository): ViewModel() {

    private val _kantinResposen = MutableLiveData<PenghasilaKantinResponse>()
    val kantinResponse : LiveData<PenghasilaKantinResponse> = _kantinResposen

    fun getPenghasilanKantin(id: String){
        viewModelScope.launch {
            val response = repository.getPenghasilanKantin(id)
            _kantinResposen.postValue(response)
        }
    }

    private val _responseTransaksi = MutableLiveData<ListTransaksiResponse>()
    val responseTransaksi: LiveData<ListTransaksiResponse> =_responseTransaksi


    fun getDataTransaksiByStatus(id: String, status: String){
        viewModelScope.launch {
            val response = repository.getDataTransaksiByStatus(id,status)
            try {
                _responseTransaksi.postValue(response)
            }catch (e : HttpException){
                withContext(Dispatchers.Main){
                    val jsonString = e.response()?.errorBody()?.string()
                    val errorBody = Gson().fromJson(jsonString, ListTransaksiResponse::class.java)
                    val errorMessage = errorBody.message
                    _responseTransaksi.postValue(errorBody)
                    Log.d("TAG", "getLogin: $errorMessage")
                }
            }catch (e : Exception){
                withContext(Dispatchers.Main){
                    Log.d("TAG", "getLogin: ${e}")
                }
            }
        }
    }
}