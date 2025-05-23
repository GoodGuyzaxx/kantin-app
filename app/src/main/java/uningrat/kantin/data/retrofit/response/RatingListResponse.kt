package uningrat.kantin.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class RatingListResponse(

	@field:SerializedName("data")
	val data: List<DataRatingList>,

	@field:SerializedName("success")
	val success: Boolean
)

data class DataRatingList(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("nama_konsumen")
	val namaKonsumen: String,

	@field:SerializedName("rating")
	val rating: Int,

	@field:SerializedName("created_at")
	val createdAt: String
)
