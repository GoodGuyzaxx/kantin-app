package uningrat.kantin.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class RatingUserResponse(

	@field:SerializedName("data")
	val data: List<DataRatingUser>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String

)

data class DataRatingUser(

	@field:SerializedName("id_menu")
	val idMenu: Int,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("rating")
	val rating: Int,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id_konsumen")
	val idKonsumen: Int,

	@field:SerializedName("id")
	val id: Int
)
