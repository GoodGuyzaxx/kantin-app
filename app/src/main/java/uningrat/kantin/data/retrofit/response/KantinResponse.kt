package uningrat.kantin.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class KantinResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("success")
	val success: Boolean
)

data class DataItem(

	@field:SerializedName("nama_kantin")
	val namaKantin: String,

	@field:SerializedName("id_admin")
	val idAdmin: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("admin_email")
	val adminEmail: String
)
