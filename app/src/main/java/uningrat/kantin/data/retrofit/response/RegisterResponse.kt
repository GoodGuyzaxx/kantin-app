package uningrat.kantin.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("data")
	val data: DataRegister
)

data class DataRegister(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("nama_konsumen")
	val namaKonsumen: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("no_telp")
	val noTelp: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String
)
