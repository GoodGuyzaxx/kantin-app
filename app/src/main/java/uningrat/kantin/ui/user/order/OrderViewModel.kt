package uningrat.kantin.ui.user.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uningrat.kantin.data.local.entity.OrderEntity
import uningrat.kantin.data.retrofit.ApiConfig
import uningrat.kantin.data.retrofit.response.OrderIdResponse
import uningrat.kantin.repository.KantinRepository

class OrderViewModel(private val repository: KantinRepository): ViewModel() {

    private var isRequsetProgress = false

    private val _responseOrderId = MutableLiveData<OrderIdResponse>()
    val responseOrderId: MutableLiveData<OrderIdResponse> = _responseOrderId

    fun getAllOrder(): LiveData<OrderEntity>{
        return repository.getAllOrder()
    }

    private var updateJob: Job? = null

    fun updateOrder(status: String) {
        // Cancel any ongoing update job
        updateJob?.cancel()

        updateJob = viewModelScope.launch {
            try {
                repository.updateOrderStatus(status)

                // Wait for the response with a timeout
                withTimeout(1000) { // 10 seconds timeout
                    while (isActive) {
                        _responseOrderId.value?.let { response ->
                            if (response.data.status == status) {
                                // We got the expected response, stop the update process
                                return@withTimeout
                            }
                        }
                        delay(500) // Check every 500ms
                    }
                }
            } catch (e: TimeoutCancellationException) {
                // Handle timeout
                Log.e("OrderViewModel", "Update order timed out")
            } catch (e: Exception) {
                // Handle other exceptions
                Log.e("OrderViewModel", "Error updating order", e)
            } finally {
                updateJob = null
            }
        }
    }


//    fun updateOrder(status: String){
//        viewModelScope.launch {
//            repository.updateOrderStatus(status)
//        }
//    }

    fun update(order: OrderEntity){
        repository.updateOrder(order)
    }
    fun getOrderID(id: String){
        val clinet = ApiConfig.getApiService().getOrderId(id)
        clinet.enqueue(object : Callback<OrderIdResponse>{
            override fun onResponse(p0: Call<OrderIdResponse>, p1: Response<OrderIdResponse>) {
                if (p1.isSuccessful){
                    _responseOrderId.value = p1.body()
                } else {
                    Log.d("TAG", "onResponse: ${p1.message()}")
                }
            }

            override fun onFailure(p0: Call<OrderIdResponse>, p1: Throwable) {
                Log.e("TAG", "onFailure: ${p1.message}", )
            }

        })
    }

//    fun getOrderID(id: String){
//        if (isRequsetProgress) return
//        viewModelScope.launch {
//            try {
//            val response = repository.getOrderId(id)
//            _responseOrderId.postValue(response)
//            }catch (e : HttpException) {
//                val jsonString = e.response()?.errorBody()?.string()
//                val errorBody = Gson().fromJson(jsonString, OrderIdResponse::class.java)
//                val errorMessage = errorBody
//                _responseOrderId.postValue(errorBody)
//                Log.d("TAG", "getLogin: $errorMessage")
//            }finally {
//                isRequsetProgress =false
//            }
//        }
//    }

}