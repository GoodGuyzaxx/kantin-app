package uningrat.kantin.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import uningrat.kantin.data.local.entity.CartEntity

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cartEntity: CartEntity)

    @Update
    fun update(cartEntity: CartEntity)

    @Delete
    fun delete(cartEntity: CartEntity)

    @Query("SELECT * FROM cart")
    fun getAllCart(): LiveData<List<CartEntity>>

    @Query("DELETE FROM cart WHERE nama_menu = :namaMenu")
    suspend fun deleteByNamaMenu(namaMenu: String)

    @Query("SELECT SUM(harga*jumlah) AS total_harga FROM CART")
    fun getTotalHarga(): LiveData<Double?>

    @Query("UPDATE cart SET jumlah = :newJumlah Where id = :cartItemid")
    suspend fun updateJumlah(cartItemid: Int, newJumlah: Int)

    @Query("DELETE FROM cart")
    suspend fun deleteAll()

}