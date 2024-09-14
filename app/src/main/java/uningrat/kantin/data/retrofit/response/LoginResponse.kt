package uningrat.kantin.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: DataLogin,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String


)

data class DataLogin(

	@field:SerializedName("id_konsumen")
	val idKonsumen: String,

	@field:SerializedName("nama_konsumen")
	val namaKonsumen: String,

	@field:SerializedName("no_telp")
	val noTelp: String,

	@field:SerializedName("email")
	val email: String
)
