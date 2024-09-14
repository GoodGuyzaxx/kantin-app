package uningrat.kantin.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class LoginAdminResponse(

	@field:SerializedName("data")
	val data: DataAdmin,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String,


)

data class DataAdmin(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("no_telp")
	val noTelp: String,

	@field:SerializedName("nama_admin")
	val namaAdmin: String,

	@field:SerializedName("email")
	val email: String
)
