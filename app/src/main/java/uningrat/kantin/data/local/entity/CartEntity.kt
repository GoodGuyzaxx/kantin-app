package uningrat.kantin.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "cart")
@Parcelize
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo("id_menu")
    var idMenu: Int? = null,

    @ColumnInfo("nama_menu")
    var namaMenu: String? = null,

    @ColumnInfo("harga")
    var harga: Int? = null,

    @ColumnInfo("jumlah")
    var jumlah: Int,

): Parcelable