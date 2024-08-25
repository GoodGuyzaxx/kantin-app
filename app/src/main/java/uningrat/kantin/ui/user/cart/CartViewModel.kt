package uningrat.kantin.ui.user.cart

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
import uningrat.kantin.data.local.entity.CartEntity
import uningrat.kantin.data.local.entity.OrderEntity
import uningrat.kantin.data.pref.KantinModel
import uningrat.kantin.data.retrofit.response.OrderItemResponse
import uningrat.kantin.repository.KantinRepository

class CartViewModel(private val repository: KantinRepository): ViewModel() {

    private val _orderResponse = MutableLiveData<OrderItemResponse>()
    val orderResponse: LiveData<OrderItemResponse> = _orderResponse

    fun getAllCart() = repository.getAllCart()

    fun getTotalHarga() = repository.getTotalHarga()

    fun getKantinSession(): LiveData<KantinModel> {
        return repository.getKantinSession().asLiveData()
    }

    fun postOrder(nama: String, namaKantin: String, email: String, totalHarga: Long){
        viewModelScope.launch {
            val repo = repository.postOrder(nama, namaKantin, email, totalHarga)
            try {
                _orderResponse.postValue(repo)
            }catch (e : HttpException){
                val jsonString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, OrderItemResponse::class.java)
                val errorMessage = errorBody.statusMessage
                _orderResponse.postValue(errorBody)
                Log.d("TAG", "getLogin: $errorMessage")
            }catch (e : Exception){
                withContext(Dispatchers.Main){
                    Log.d(TAG, "postOrder: ${e.message}")
                }
            }
        }
    }

    fun insertOrder(orderEntity: OrderEntity){
        repository.insertOrder(orderEntity)
    }

    suspend fun deleteCart(){
        repository.deleteAll()
    }

    companion object {
        private val TAG = CartViewModel::class.java.simpleName
    }

}