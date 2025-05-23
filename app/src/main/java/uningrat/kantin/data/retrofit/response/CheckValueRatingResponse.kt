package uningrat.kantin.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class CheckValueRatingResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)
