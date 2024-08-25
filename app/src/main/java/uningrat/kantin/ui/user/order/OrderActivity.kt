package uningrat.kantin.ui.user.order

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import uningrat.kantin.R
import uningrat.kantin.data.local.entity.OrderEntity
import uningrat.kantin.databinding.ActivityOrderBinding
import uningrat.kantin.ui.ViewModelFactory

class OrderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOrderBinding
    private val orderViewModel by viewModels<OrderViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id ="9d7eabaf-cfe6-48f6-9b70-b93a75d68193"

        orderViewModel.getOrderID(id)
        orderViewModel.responseOrderId.observe(this){
            Log.d("TAG", "onCreate: ${it.nama}")
        }

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
            }
        }

    }
}