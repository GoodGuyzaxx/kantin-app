package uningrat.kantin.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class TransaksiByMonthResponse(
	@field:SerializedName("data")
	val data: DataTransaksiByMonth,

	@field:SerializedName("success")
	val success: Boolean
)

data class DataTransaksiByMonth(
	@field:SerializedName("month")
	val month: String,

	@field:SerializedName("year")
	val year: Int,

	@field:SerializedName("total_amount")
	val totalAmount: Int,

	@field:SerializedName("total_transactions")
	val totalTransactions: Int,

)
