package uningrat.kantin.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class MenuKantinResponse(

	@field:SerializedName("totalmenu")
	val totalmenu: Int,

	@field:SerializedName("data")
	val data: List<DataMenu>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataMenu(

	@field:SerializedName("id_menu")
	val idMenu: Int,

	@field:SerializedName("nama_menu")
	val namaMenu: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("kategori")
	val kategori: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("stock")
	val stock: Int,

	@field:SerializedName("id_kantin")
	val idKantin: Int,

	@field:SerializedName("gambar")
	val gambar: String
)
