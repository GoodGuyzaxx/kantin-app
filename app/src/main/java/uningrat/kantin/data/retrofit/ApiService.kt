package uningrat.kantin.data.retrofit

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import uningrat.kantin.data.retrofit.response.DataOrder
import uningrat.kantin.data.retrofit.response.KantinResponse
import uningrat.kantin.data.retrofit.response.LoginResponse
import uningrat.kantin.data.retrofit.response.MenuKantinResponse
import uningrat.kantin.data.retrofit.response.MenuResponse
import uningrat.kantin.data.retrofit.response.OrderIdResponse
import uningrat.kantin.data.retrofit.response.OrderItemResponse
import uningrat.kantin.data.retrofit.response.RatingResponse
import uningrat.kantin.data.retrofit.response.RegisterResponse
import uningrat.kantin.repository.KantinRepository

interface ApiService{

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("konsumen/login")
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("konsumen/register")
    suspend fun postRegister(
        @Field("nama_konsumen") nama_konsuemn : String,
        @Field("email") email : String,
        @Field("no_telp") noTelp : String,
        @Field("password") password: String
    ): RegisterResponse

    @Headers("Accept: application/json")
    @GET("kantin")
    fun getListKantin(): Call<KantinResponse>

    @Headers("Accept: application/json")
    @GET("menu/kantin/{id}")
    fun getMenuByKantinId(
        @Path("id") id :String
    ): Call<MenuKantinResponse>

    @Headers("Accept: application/json")
    @GET("makanan/kantin/{id}")
    fun getMenuMakanan(
        @Path("id") id : String
    ): Call<MenuResponse>

    @Headers("Accept: application/json")
    @GET("minuman/kantin/{id}")
    fun getMenuMinuman(
        @Path("id") id : String
    ): Call<MenuResponse>


    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("order/buy")
    suspend fun postOrder(
        @Field("name") nama : String,
        @Field("nama_kantin") namaKantin : String,
        @Field("email") email : String,
        @Field("total_harga") totalHarga : Long
    ): OrderItemResponse

    @Headers("Accept: application/json")
    @GET("order/id/{id}")
    fun getOrderId(
        @Path("id") id :String
    ): Call<DataOrder>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("rating")
    suspend fun postRating(
        @Field("id_konsumen") idKonsumen : Int,
        @Field("id_menu") idMenu : Int,
        @Field("rating") rating : Int,
    ): RatingResponse

}