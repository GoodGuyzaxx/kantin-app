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
    var id: Int? = null,

    @ColumnInfo(name = "order_id")
    var orderId: String? = null,

    @ColumnInfo(name = "total")
    var total: Int? = null,

    @ColumnInfo(name = "status")
    var status: String? = null,

    @ColumnInfo(name = "gambar_qr")
    var gambarQr: String? = null,

    @ColumnInfo(name = "payment_link")
    var paymentLink: String? = null,
): Parcelable