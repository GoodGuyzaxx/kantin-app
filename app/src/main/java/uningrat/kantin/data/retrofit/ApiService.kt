package uningrat.kantin.data.retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import uningrat.kantin.data.retrofit.response.AddMenuResponse
import uningrat.kantin.data.retrofit.response.KantinResponse
import uningrat.kantin.data.retrofit.response.ListTransaksiResponse
import uningrat.kantin.data.retrofit.response.LoginAdminResponse
import uningrat.kantin.data.retrofit.response.LoginResponse
import uningrat.kantin.data.retrofit.response.MenuResponse
import uningrat.kantin.data.retrofit.response.OrderIdResponse
import uningrat.kantin.data.retrofit.response.OrderItemResponse
import uningrat.kantin.data.retrofit.response.PenghasilaKantinResponse
import uningrat.kantin.data.retrofit.response.RatingResponse
import uningrat.kantin.data.retrofit.response.RatingUserResponse
import uningrat.kantin.data.retrofit.response.RegisterResponse
import uningrat.kantin.data.retrofit.response.RekomendasiResponse
import uningrat.kantin.data.retrofit.response.UpdateProfileResponse

interface ApiService{

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("admin/login")
    suspend fun postAdminLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginAdminResponse

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("konsumen/login")
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @PATCH("konsumen/{id}")
    suspend fun updateProfile(
        @Path("id") id: String,
        @Field("email") email: String,
        @Field("no_telp") noTelp: String
    ): UpdateProfileResponse

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
    suspend fun getMenuByKantinId(
        @Path("id") id :String
    ): MenuResponse

    @Headers("Accept: application/json")
    @DELETE("menu/{id}")
    fun deleteMenuByKantinId(
        @Path("id") id :String
    ): Call<MenuResponse>

    @Headers("Accept: application/json")
    @GET("menu")
    suspend fun getAllMenu(): MenuResponse

    @Headers("Accept: application/json")
    @Multipart
    @POST("menu")
    suspend fun addMenu(
        @Part("id_kantin") idKantin : RequestBody,
        @Part("nama_menu") namaMenu : RequestBody,
        @Part("deskripsi") deskripsi : RequestBody,
        @Part("harga") harga : RequestBody,
        @Part("stock") stock : RequestBody,
        @Part("kategori") kategori : RequestBody,
        @Part file : MultipartBody.Part,
    ): AddMenuResponse

    @Headers("Accept: application/json")
    @Multipart
    @POST("menu/{id}")
    suspend fun editMenu(
        @Path("id") id : String,
        @Part("id_kantin") idKantin : RequestBody,
        @Part("nama_menu") namaMenu : RequestBody,
        @Part("deskripsi") deskripsi : RequestBody,
        @Part("harga") harga : RequestBody,
        @Part("stock") stock : RequestBody,
        @Part("kategori") kategori : RequestBody,
        @Part file : MultipartBody.Part,
        @Part("_method") _method : RequestBody,
    ): AddMenuResponse

    @Headers("Accept: application/json")
    @GET("kategori/makanan/kantin/{id}")
    fun getMenuMakanan(
        @Path("id") id : String
    ): Call<MenuResponse>

    @Headers("Accept: application/json")
    @GET("kategori/minuman/kantin/{id}")
    fun getMenuMinuman(
        @Path("id") id : String
    ): Call<MenuResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("order/buy")
    suspend fun postOrder(
        @Field("order_id") orderId: String,
        @Field("name") nama : String,
        @Field("nama_kantin") namaKantin : String,
        @Field("email") email : String,
        @Field("total_harga") totalHarga : Long
    ): OrderItemResponse

    @Headers("Accept: application/json")
    @GET("order/id/{id}")
    fun getOrderId(
        @Path("id") id :String
    ): Call<OrderIdResponse>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("rating")
    suspend fun postRating(
        @Field("id_konsumen") idKonsumen : Int,
        @Field("id_menu") idMenu : Int,
        @Field("rating") rating : Int,
    ): RatingResponse

    @Headers("Accept: application/json")
    @GET("rating/{id_konsumen}/{id_menu}")
    suspend fun getRatingUser(
        @Path ("id_konsumen") idKonsumen : Int,
        @Path ("id_menu") idMenu : Int
    ): RatingUserResponse

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @PATCH("rating/{id_konsumen}/{id_menu}")
    suspend fun updateRatingUser(
        @Path ("id_konsumen") idKonsumen : Int,
        @Path ("id_menu") idMenu : Int,
        @Field ("rating") rating : Int
    ): RatingUserResponse

    @Headers("Accept: application/json")
    @GET("transaksi/email/{id}")
    suspend fun getDataTransaksi(
        @Path ("id") id : String,
    ): ListTransaksiResponse

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("transaksi")
    suspend fun postDataTransaksi(
        @Field ("id_order") idOrder : String,
        @Field ("id_kantin") idKantin : Int,
        @Field ("total_harga") totalHarga : Int,
        @Field ("id_menu") idMenu : Int,
        @Field ("menu") menu : String,
        @Field ("jumlah") jumlah : Int,
        @Field ("tipe_pembayaran") tipePembayaran : String,
        @Field ("status_pembayaran") statusPembayaran : String,
        @Field ("email_konsumen") emailKonsumen : String,
        @Field ("nama_konsumen") namaKonsumen : String
    ): AddMenuResponse

    @Headers("Accept: application/json")
    @GET("transaksi/status/{id}/{status}")
    suspend fun getDataTransaksiByStatus(
        @Path ("id") id : String,
        @Path ("status") status : String
    ): ListTransaksiResponse

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @PATCH("transaksi/id/{id}")
    suspend fun updateDataTransaksi(
        @Path ("id") id : String,
        @Field ("status_pesanan") statusPesanan : String,
    ): ListTransaksiResponse


    @Headers("Accept: application/json")
    @GET("transaksi/kantin/{id}")
    suspend fun getPenghasilanKantin(
        @Path ("id") id : String,
    ): PenghasilaKantinResponse

    @Headers("Accept: application/json")
    @GET("rekomendasi/menu")
    suspend fun getRekomendasiMenu(
    ): RekomendasiResponse

}