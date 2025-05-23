package uningrat.kantin.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class RegisterAdminResponse(

	@field:SerializedName("data")
	val data: DataAdminRegister,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataAdminRegister(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("id_admin")
	val idAdmin: Int,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("no_telp")
	val noTelp: String,

	@field:SerializedName("nama_admin")
	val namaAdmin: String,

	@field:SerializedName("email")
	val email: String
)
