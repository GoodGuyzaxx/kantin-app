package uningrat.kantin.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("nama_konsumen")
	val namaKonsumen: String,

	@field:SerializedName("email")
	val email: String
)
