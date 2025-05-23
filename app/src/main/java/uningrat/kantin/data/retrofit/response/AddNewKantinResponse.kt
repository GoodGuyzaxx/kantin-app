package uningrat.kantin.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class AddNewKantinResponse(

	@field:SerializedName("data")
	val data: DataNewKantin,

	@field:SerializedName("success")
	val success: Boolean
)

data class DataNewKantin(

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("nama_kantin")
	val namaKantin: String,

	@field:SerializedName("id_admin")
	val idAdmin: Int,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id_kantin")
	val idKantin: Int
)
