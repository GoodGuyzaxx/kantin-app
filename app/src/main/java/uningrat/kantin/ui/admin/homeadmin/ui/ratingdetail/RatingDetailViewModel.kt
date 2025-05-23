package uningrat.kantin.ui.admin.homeadmin.ui.ratingdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import uningrat.kantin.data.retrofit.response.RatingListResponse
import uningrat.kantin.repository.KantinRepository


class RatingDetailViewModel(private val repository: KantinRepository): ViewModel(){

    private val _ratingResponse = MutableLiveData<RatingListResponse>()
    val ratingResponse : LiveData<RatingListResponse> = _ratingResponse

    fun getRatingByMenu(id : String){
        viewModelScope.launch {
            try {
                val response = repository.getRatingByMenu(id)
                _ratingResponse.postValue(response)
            }catch (e : HttpException){

            }
        }
    }
}