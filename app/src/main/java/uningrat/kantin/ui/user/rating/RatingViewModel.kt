package uningrat.kantin.ui.user.rating

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
import uningrat.kantin.data.retrofit.response.RatingResponse
import uningrat.kantin.data.retrofit.response.RatingUpdateResponse
import uningrat.kantin.data.retrofit.response.RatingUserResponse
import uningrat.kantin.repository.KantinRepository

class RatingViewModel (private val repository: KantinRepository): ViewModel() {

    fun getSession(): LiveData<UserModel> = repository.getSession().asLiveData()

    private val _ratingResponse = MutableLiveData<RatingResponse>()
    val ratingResponse: LiveData<RatingResponse> = _ratingResponse

    private val _ratingUserResponse = MutableLiveData<RatingUserResponse>()
    val ratingUserResponse: LiveData<RatingUserResponse> = _ratingUserResponse

    private val _ratingUpdateResponse = MutableLiveData<RatingUpdateResponse>()
    val ratingUpdateResponse: LiveData<RatingUpdateResponse> = _ratingUpdateResponse

    fun postRating (idKonsumen: Int, idKantin: Int, rating: Int) {
        viewModelScope.launch {
            try {
                val response = repository.postRating(idKonsumen, idKantin, rating)
                _ratingResponse.postValue(response)
            }catch (e : HttpException){
                val jsonString = e.response()?.body().toString()
                val errorBody = Gson().fromJson(jsonString, RatingResponse::class.java)
                val errorMessage = errorBody.message
                _ratingResponse.postValue(errorBody)
                Log.d("TAG", "postRating: $errorMessage")
            }
        }
    }

    fun getRatingUser (idKonsumen: Int,idMenu: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getRatingUser(idKonsumen,idMenu)
                _ratingUserResponse.postValue(response)
            }catch (e : HttpException){
                val jsonString = e.response()?.body()?.toString()
                val errorBody = Gson().fromJson(jsonString, RatingUserResponse::class.java)
                val errorMessage = errorBody.message
                Log.d("TAG", "postRating: $errorMessage")
                _ratingUserResponse.postValue(errorBody)
            }
        }
    }

    fun updateRatingUser (idKonsumen: Int,idMenu: Int,rating: Int) {
        viewModelScope.launch {
            try {
                val response = repository.updateRatingUser(idKonsumen, idMenu, rating)
                _ratingUpdateResponse.postValue(response)
            } catch (e: HttpException) {
                val jsonString = e.response()?.body()?.toString()
                val errorBody = Gson().fromJson(jsonString, RatingUpdateResponse::class.java)
                val errorMessage = errorBody.message
                Log.d("TAG", "postRating: $errorMessage")
                _ratingUpdateResponse.postValue(errorBody)
            } catch (e : Exception) {
                val errorMessage = e.message
                Log.d("TAG", "postRating: $errorMessage")
            }
        }
    }

}