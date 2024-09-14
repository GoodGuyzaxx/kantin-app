package uningrat.kantin.ui.user.order

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uningrat.kantin.R
import uningrat.kantin.data.local.entity.OrderEntity
import uningrat.kantin.databinding.ActivityOrderBinding
import uningrat.kantin.ui.ViewModelFactory

class OrderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderBinding
    private val orderViewModel by viewModels<OrderViewModel> {
        ViewModelFactory.getInstance(this)
    }
//    private val handler = Handler(Looper.getMainLooper())
//    private lateinit var  runnable: Runnable
    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showDataOrder()

        val actionBar = supportActionBar
        actionBar?.title = "Order"
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)


        orderViewModel.responseOrderId.observe(this) {
            val dataStatus = it.data.status
            orderViewModel.updateOrder(dataStatus)
        }

//        updateStatusOrder()

//        val context = binding.root.context
//        val database = KantinDatabase.getDatabase(context)
//        CoroutineScope(Dispatchers.IO).launch {
//            database.orderDao().updateStatus("PENDING")
//        }

        binding.layoutRefresh.setOnRefreshListener {
            orderViewModel.getAllOrder().observe(this@OrderActivity){ dataOrder ->
                val dataId = dataOrder.orderId
                orderViewModel.getOrderID(dataId)
            }
            binding.layoutRefresh.isRefreshing = false
        }

    }

    private fun delayReq(){
        coroutineScope.launch {
            showDataOrder()
            delay(10000)
//            startRepeatingOrder()
            updateStatusOrder()
        }
    }

    private fun showDataOrder(){
        orderViewModel.getAllOrder().observe(this){dataOrder->
            if (dataOrder != null){
                Glide
                    .with(binding.root.context)
                    .load(dataOrder.gambarQr)
                    .into(binding.ivQrCode)
                val convertToLong = dataOrder.total
                binding.tvOrderStatus.text = dataOrder.status
                binding.tvOrderTotalAmount.text = resources.getString(R.string.mata_uang, convertToLong)

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

//    private fun startRepeatingOrder(){
//        runnable = object : Runnable{
//            override fun run() {
//                orderViewModel.getAllOrder().observe(this@OrderActivity){
//                    val dataId = it.orderId
//                    orderViewModel.getOrderID(dataId)
//                }
//                handler.postDelayed(this, 10000)
//            }
//        }
//        handler.post(runnable)
//    }


//    private var responseObserver: Observer<OrderIdResponse>? = null

    private fun updateStatusOrder(){
//        responseObserver?.let { orderViewModel.responseOrderId.removeObserver(it) }
//
//        orderViewModel.getAllOrder().observe(this) {order ->
//            val dataID = order.orderId
//
//            responseObserver = Observer<OrderIdResponse> {dataOrder ->
//                val newDataResponse = dataOrder.data.status
//                orderViewModel.updateOrder(dataID, newDataResponse)
//            }
//
//            responseObserver?.let { orderViewModel.responseOrderId.observe(this, it) }
//        }

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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

//    private fun stopRepetingTask(){
//        handler.removeCallbacks(runnable)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        stopRepetingTask()
//    }
}