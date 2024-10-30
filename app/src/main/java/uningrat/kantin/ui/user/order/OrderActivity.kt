package uningrat.kantin.ui.user.order

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import uningrat.kantin.data.local.entity.OrderEntity
import uningrat.kantin.databinding.ActivityOrderBinding
import uningrat.kantin.ui.ViewModelFactory
import java.io.File
import java.text.NumberFormat
import java.util.Locale

class OrderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderBinding
    private val orderViewModel by viewModels<OrderViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showDataOrder()

        val actionBar = supportActionBar
        actionBar?.title = "Order"
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)


        binding.layoutRefresh.setOnRefreshListener {
            orderViewModel.getAllOrder().observe(this@OrderActivity){ dataOrder ->
                if (dataOrder == null){
                    Toast.makeText(this, "Data Order Kosong", Toast.LENGTH_SHORT).show()
                } else {
                    val dataId = dataOrder.orderId ?: 0
                    orderViewModel.getOrderID(dataId.toString())

                    orderViewModel.responseOrderId.observe(this) {
                        val dataStatus = it.data.status
                        orderViewModel.updateOrder(dataStatus)
                    }
                }
            }
            checkAndDeleteCompletedOrders()
            binding.layoutRefresh.isRefreshing = false
        }

        binding.btnDownload.setOnClickListener {
            orderViewModel.getAllOrder().observe(this){dataCart ->
                val name = "qr"
                val dataQR = dataCart?.gambarQr
                if (dataQR != null) {
                    downloadImageNew(name,dataQR)
                }
            }
        }

    }


    private fun showDataOrder(){
        val formmater = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        orderViewModel.getAllOrder().observe(this){dataOrder->
            if (dataOrder != null){
                Glide
                    .with(binding.root.context)
                    .load(dataOrder.gambarQr)
                    .into(binding.ivQrCode)
//                val convertToLong = dataOrder.total
                binding.tvOrderStatus.text = dataOrder.status
                binding.tvOrderTotalAmount.text = formmater.format(dataOrder.total?.toDouble())

                binding.btnBayar.setOnClickListener {
                    val deepLink = dataOrder.paymentLink
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(deepLink))
                    startActivity(i)
                }
                binding.layoutOrderNull.visibility = View.GONE
                binding.layoutOrderNotNull.visibility = View.VISIBLE
            } else {
                binding.layoutOrderNotNull.visibility = View.GONE
                binding.layoutOrderNull.visibility = View.VISIBLE
            }
        }
    }

    private fun updateStatusOrder(){
        orderViewModel.getAllOrder().observe(this) {dataOrder ->
            orderViewModel.responseOrderId.observe(this) {
                val entity = OrderEntity(
                    id = dataOrder.id,
                    total = dataOrder.total,
                    orderId = dataOrder.orderId,
                    status = it.data.status,
                    gambarQr = dataOrder.gambarQr,
                    paymentLink = dataOrder.paymentLink
                )
                orderViewModel.update(entity)
            }
        }
    }

    private fun checkAndDeleteCompletedOrders() {
        orderViewModel.getAllOrder().observe(this) { dataOrder ->
            if (dataOrder != null) {
                when (dataOrder.status) {
                    "Paid", "Expired" -> {
                        orderViewModel.deleteAllOrder()
                    }
                }
            } else {
                Toast.makeText(this, "Data Order Kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun downloadImageNew(filename: String, downloadUrlOfImage: String) {
        try {
            val dm = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadUri = Uri.parse(downloadUrlOfImage)
            val request = DownloadManager.Request(downloadUri)
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/jpeg") // Tipe file Anda. Anda bisa menggunakan kode ini untuk mengunduh tipe file lain juga.
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator + filename + ".jpg"
                )

            dm.enqueue(request)
            Toast.makeText(this, "Image download started.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Image download failed.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }


}