package uningrat.kantin.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity (tableName = "order")
@Parcelize
data class OrderEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "order_id")
    var orderId: String,

    @ColumnInfo(name = "total")
    var total: Int,

    @ColumnInfo(name = "status")
    var status: String,

    @ColumnInfo(name = "gambar_qr")
    var gambarQr: String,

    @ColumnInfo(name = "payment_link")
    var paymentLink: String,
): Parcelable