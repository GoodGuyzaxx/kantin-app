package uningrat.kantin.repository

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uningrat.kantin.data.local.entity.CartEntity
import uningrat.kantin.data.local.entity.OrderEntity
import uningrat.kantin.data.local.room.CartDao
import uningrat.kantin.data.local.room.KantinDatabase
import uningrat.kantin.data.local.room.OrderDao
import uningrat.kantin.data.pref.DataPreferences
import uningrat.kantin.data.pref.KantinModel
import uningrat.kantin.data.pref.UserModel
import uningrat.kantin.data.retrofit.ApiService
import uningrat.kantin.data.retrofit.response.DataOrder
import uningrat.kantin.data.retrofit.response.KantinResponse
import uningrat.kantin.data.retrofit.response.LoginResponse
import uningrat.kantin.data.retrofit.response.OrderIdResponse
import uningrat.kantin.data.retrofit.response.OrderItemResponse
import uningrat.kantin.data.retrofit.response.RatingResponse
import uningrat.kantin.data.retrofit.response.RegisterResponse
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class KantinRepository private constructor(
    private val apiService: ApiService,
    private val userPreferences: DataPreferences,
    private var mCartDao: CartDao,
    private var mOrderDao: OrderDao,
    private val application: Application){

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    /*REPO FOR HTTP REQUEST*/
    suspend fun postLogin(email:String, password:String): LoginResponse {
        return apiService.postLogin(email,password)
    }

    suspend fun postRegister(nama: String, email: String, noTelp:String, password: String): RegisterResponse {
        return apiService.postRegister(nama,email,noTelp,password)
    }

    suspend fun postRating(idKosnumen: Int, idMenu: Int, rating: Int): RatingResponse {
        return apiService.postRating(idKosnumen,idMenu,rating)
    }

    suspend fun postOrder(nama: String, namaKantin: String, email: String, totalHarga: Long): OrderItemResponse {
        return apiService.postOrder(nama, namaKantin, email, totalHarga)
    }

//    suspend fun getOrderId(id: String): DataOrder{
//        return apiService.getOrderId(id)
//    }

    /*REPO FOR DATASTORE*/
    suspend fun saveSession(user: UserModel){
        userPreferences.saveSession(user)
    }

    fun getSession(): Flow<UserModel>{
        return  userPreferences.getSession()
    }

    suspend fun saveKantinSession(kantin: KantinModel){
        userPreferences.saveKantinSession(kantin)
    }

    fun getKantinSession(): Flow<KantinModel>{
        return userPreferences.getKantinSession()
    }

    /*REPO FOR DATABASE*/
    suspend fun logout() = userPreferences.logout()

    init {
        val db = KantinDatabase.getDatabase(application)
        mCartDao = db.cartDao()
    }

    suspend fun deleteAll() {
        mCartDao.deleteAll()
    }

    fun getAllCart(): LiveData<List<CartEntity>> {
        return mCartDao.getAllCart()
    }

    fun getTotalHarga():LiveData<Double?>{
        return mCartDao.getTotalHarga()
    }

    fun insertOrder(order: OrderEntity ){
        executorService.execute { mOrderDao.insert(order) }
    }

    suspend fun updateOrderStatus(id: String, status: String){
        return mOrderDao.updateStatus(id, status)
    }

    fun getAllOrder(): LiveData<OrderEntity>{
        return mOrderDao.getAllOrder()
    }


    companion object {
        @Volatile
        private var instace: KantinRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreferences: DataPreferences,
            mCartDao: CartDao,
            mOrderDao: OrderDao,
            application: Application
        ): KantinRepository =
            instace ?: synchronized(this){
                instace ?: KantinRepository(apiService,userPreferences,mCartDao,mOrderDao,application)
            }.also { instace = instace }
    }
}