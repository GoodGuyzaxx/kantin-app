package uningrat.kantin.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class RekomendasiResponse(

	@field:SerializedName("data")
	val data: List<DataRekomendasiMenu>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataRekomendasiMenu(

	@field:SerializedName("id_rekomendasi")
	val idRekomendasi: Int,

	@field:SerializedName("id_menu")
	val idMenu: Int,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("nama_kantin")
	val namaKantin: String,

	@field:SerializedName("rating")
	val rating: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("gambar")
	val gambar: String
)
