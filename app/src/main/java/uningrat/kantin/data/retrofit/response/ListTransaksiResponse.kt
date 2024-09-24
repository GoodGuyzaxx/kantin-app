package uningrat.kantin.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class ListTransaksiResponse(

	@field:SerializedName("data")
	val data: List<DataTransaksi>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataTransaksi(

	@field:SerializedName("id_order")
	val idOrder: String,

	@field:SerializedName("status_pembayaran")
	val statusPembayaran: String,

	@field:SerializedName("email_konsumen")
	val emailKonsumen: String,

	@field:SerializedName("nama_konsumen")
	val namaKonsumen: String,

	@field:SerializedName("tipe_pembayaran")
	val tipePembayaran: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("total_harga")
	val totalHarga: Int,

	@field:SerializedName("menu")
	val menu: List<DetailMenuItem>,

	@field:SerializedName("id_kantin")
	val idKantin: Int,

	@field:SerializedName("status_pesanan")
	val statusPesanan: String
)

data class DetailMenuItem(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("jumlah")
	val jumlah: Int
)
