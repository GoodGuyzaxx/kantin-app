package uningrat.kantin.data.retrofit.response

import com.google.gson.annotations.SerializedName

data class OrderIdResponse(

	@field:SerializedName("data")
	val data: DataOrder,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("messsage")
	val messsage: String
)

data class DataOrder(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("total_amount")
	val totalAmount: Int,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("status")
	val status: String
)
