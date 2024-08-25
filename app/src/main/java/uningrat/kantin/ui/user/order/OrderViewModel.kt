package uningrat.kantin.ui.user.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import uningrat.kantin.data.local.entity.OrderEntity
import uningrat.kantin.data.retrofit.ApiConfig
import uningrat.kantin.data.retrofit.response.DataOrder
import uningrat.kantin.data.retrofit.response.OrderIdResponse
import uningrat.kantin.repository.KantinRepository

class OrderViewModel(private val repository: KantinRepository): ViewModel() {

    private val _responseOrderId = MutableLiveData<DataOrder>()
    val responseOrderId: LiveData<DataOrder> = _responseOrderId

    fun getAllOrder(): LiveData<OrderEntity>{
        return repository.getAllOrder()
    }

    fun updateOrder(id: String, status: String){
        viewModelScope.launch {
            repository.updateOrderStatus(id, status)
        }
    }

    fun getOrderID(id: String){
        val client = ApiConfig.getApiService().getOrderId(id)
        client.enqueue(object : Callback<DataOrder>{
            override fun onResponse(p0: Call<DataOrder>, p1: Response<DataOrder>) {
                if (p1.isSuccessful){
                    _responseOrderId.value = p1.body()
                } else {
                    Log.e("TAG", "onResponse: ${p1.message()}", )
                }
            }

            override fun onFailure(p0: Call<DataOrder>, p1: Throwable) {
                Log.e("TAG", "onFailure: ${p1.message.toString()}", )
            }

        })
    }
}