package uningrat.kantin.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class PenghasilaKantinResponse(

	@field:SerializedName("data")
	val data: DataItemKantin,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataItemKantin(

	@field:SerializedName("total_harga_kantin")
	val totalHargaKantin: String,

	@field:SerializedName("id_kantin")
	val idKantin: Int
)
