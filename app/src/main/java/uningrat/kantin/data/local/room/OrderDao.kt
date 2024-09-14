package uningrat.kantin.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import uningrat.kantin.data.local.entity.OrderEntity

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(orderEntity: OrderEntity)

    @Update
    fun update(orderEntity: OrderEntity)

    @Query("SELECT * FROM `order`")
    fun getAllOrder(): LiveData<OrderEntity>

    @Query("SELECT * FROM `order` WHERE id = :id")
    fun getOrderByid(id: String): LiveData<OrderEntity>

    @Query("UPDATE `order` SET status = :status")
    suspend fun updateStatus(status: String)

    @Query("DELETE FROM `order`")
    suspend fun deleteAll()
}