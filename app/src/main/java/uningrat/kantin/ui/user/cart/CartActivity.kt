package uningrat.kantin.ui.user.cart

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uningrat.kantin.R
import uningrat.kantin.adapter.CartAdapter
import uningrat.kantin.data.local.entity.CartEntity
import uningrat.kantin.data.local.entity.OrderEntity
import uningrat.kantin.data.pref.KantinModel
import uningrat.kantin.databinding.ActivityCartBinding
import uningrat.kantin.ui.ViewModelFactory
import uningrat.kantin.ui.user.Home.HomeActivity
import uningrat.kantin.ui.user.kantin.KantinActivity
import uningrat.kantin.ui.user.order.OrderActivity
import kotlin.math.log

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private var metodePembayaran = "Tunai"
    private val cartViewModel by viewModels<CartViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateTotalHarga()
        payment()

        val layoutManager = LinearLayoutManager(this)
        binding.rvCart.layoutManager =layoutManager

        cartViewModel.getAllCart().observe(this){
            setUpRecycleView(it)
        }

        binding.btnMetodePembayaran.setOnClickListener {
            showCostumeDialog()
        }

    }

    private fun showCostumeDialog(){
        val dialog = Dialog(this@CartActivity)
        dialog.setContentView(R.layout.dialog_payment_method)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val radioGroup = dialog.findViewById<RadioGroup>(R.id.rg_metode_pembayaran)
        val btnPilih = dialog.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btn_pilih)

        btnPilih.setOnClickListener {
            when(radioGroup.checkedRadioButtonId){
                R.id.rb_tunai -> {
                    metodePembayaran = "TUNAI"
                    binding.btnMetodePembayaran.text = metodePembayaran
                }
                R.id.rb_non_tunai -> {
                    metodePembayaran = "NON TUNAI"
                    binding.btnMetodePembayaran.text = metodePembayaran
                }
                else -> {
                    Toast.makeText(this, "Pilih Metode Pembayaran", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showSuccessCashPaymentDialog(){
        val dialog = Dialog(this@CartActivity)
        dialog.setContentView(R.layout.dialog_success_payment)
        cartViewModel.getTotalHarga().observe(this) { totalHarga ->
            val convertToDouble = totalHarga?.toLong() ?: 0L
            val textValue=dialog.findViewById<TextView>(R.id.tv_dialog_total_harga_value)
            textValue.text = convertToDouble.toString()

        }

        val btnOk = dialog.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btn_dialog_payment_ok)
        btnOk.setOnClickListener {
            val intent = Intent(this@CartActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
            deleteCart()
        }
        dialog.show()
    }

    private fun showSuccessNonTunaiDialog(){
        val dialog = Dialog(this@CartActivity)
        dialog.setContentView(R.layout.dialog_success_payment)
        cartViewModel.getTotalHarga().observe(this) { totalHarga ->
            val convertToDouble = totalHarga?.toLong() ?: 0L
            val textValue=dialog.findViewById<TextView>(R.id.tv_dialog_total_harga_value)
            textValue.text = convertToDouble.toString()

        }

        val btnOk = dialog.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btn_dialog_payment_ok)
        btnOk.setOnClickListener {
            val intent = Intent(this@CartActivity, OrderActivity::class.java)
            startActivity(intent)
            finish()
            deleteCart()
        }
        dialog.show()

    }

    private fun updateTotalHarga(){
        cartViewModel.getTotalHarga().observe(this){totalHarga->
            val convertToDouble = totalHarga?.toLong()
            if (convertToDouble != null){
                binding.tvTotalHargaValue.text = resources.getString(R.string.mata_uang, convertToDouble)
            } else {
                binding.tvTotalHargaValue.text = resources.getString(R.string.mata_uang, 0)
            }
        }
    }

    private fun payment(){
        cartViewModel.getTotalHarga().observe(this) { totalHarga ->
            val convertToDouble = totalHarga?.toLong() ?: 0L

            cartViewModel.getKantinSession().observe(this) { kantinSession ->
                binding.btnLanjut.setOnClickListener {
                    if (convertToDouble > 0) {
                        when (metodePembayaran) {
                            "TUNAI" -> {
                                processCashPayment(kantinSession, convertToDouble)
                                showSuccessCashPaymentDialog()
                            }
                            "NON TUNAI" -> {
                                processNonCashPayment(kantinSession, convertToDouble)
                                cartViewModel.orderResponse.observe(this){ data ->
                                    val dataGambarQr = data.actions[0].url
                                    val dataPaymentLink = data.actions[1].url
                                    val convertToDouble =data.grossAmount.toDouble()
                                    val convertToInt = convertToDouble.toInt()
                                    val order = OrderEntity(
                                        total = convertToInt,
                                        orderId = data.orderId,
                                        status = data.transactionStatus,
                                        gambarQr =  dataGambarQr,
                                        paymentLink = dataPaymentLink
                                    )
                                    cartViewModel.insertOrder(order)
                                }
                                showSuccessNonTunaiDialog()
                            }
                            else -> Toast.makeText(this, "Pilih metode pembayaran", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Keranjang kosong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun processCashPayment(kantinSession: KantinModel, convertToDouble: Long) {
        Toast.makeText(this, "Pembayaran tunai sebesar ${convertToDouble}", Toast.LENGTH_SHORT).show()

    }

    private fun processNonCashPayment(kantinSession: KantinModel, convertToDouble: Long) {
        val nama = kantinSession.id
        val namaKantin = "Kantin"
        val email = kantinSession.email
        cartViewModel.postOrder(nama, namaKantin, email, convertToDouble)
    }

    private fun deleteCart(){
        CoroutineScope(Dispatchers.IO).launch {
            cartViewModel.deleteCart()
        }
    }


    private fun setUpRecycleView(data : List<CartEntity>){
        val adapter = CartAdapter()
        adapter.submitList(data)
        binding.rvCart.adapter = adapter

    }
}