package uningrat.kantin.ui.user.cart

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
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
import uningrat.kantin.ui.user.order.OrderActivity
import java.text.NumberFormat
import java.util.Locale
import java.util.UUID

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private var metodePembayaran = "Tunai"
    private val cartViewModel by viewModels<CartViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val uuidGenerate = UUID.randomUUID()
    private val uuidToString = uuidGenerate.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = "Cart"
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

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
            val convertToDouble = totalHarga ?: 0L
            val textValue=dialog.findViewById<TextView>(R.id.tv_dialog_total_harga_value)
            textValue.text = convertToDouble.toString()
        }

        val btnOk = dialog.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btn_dialog_payment_ok)
        btnOk.setOnClickListener {
            val intent = Intent(this@CartActivity, HomeActivity::class.java)
            startActivity(intent)
            deleteCart()
            finish()
        }
        dialog.show()
    }

    private fun showSuccessNonTunaiDialog(){
        val dialog = Dialog(this@CartActivity)
        dialog.setContentView(R.layout.dialog_success_payment)
            val textValue=dialog.findViewById<TextView>(R.id.tv_dialog_total_harga_value)
            textValue.visibility = View.GONE

        val btnOk = dialog.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btn_dialog_payment_ok)
        btnOk.setOnClickListener {
            val intent = Intent(this@CartActivity, OrderActivity::class.java)
            startActivity(intent)
            deleteCart()
            finish()
        }
        dialog.show()

    }

    private fun updateTotalHarga(){
        val formmater = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        cartViewModel.getTotalHarga().observe(this){totalHarga->
            val convertToDouble = totalHarga?.toLong()
            if (convertToDouble != null){
                binding.tvTotalHargaValue.text = formmater.format(totalHarga)
            } else {
                binding.tvTotalHargaValue.text = formmater.format(0)
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
                                processNonCashPaymentTransaksi(kantinSession)

                                //Insert Data to ROOM
                                cartViewModel.orderResponse.observe(this){ data ->
                                    val dataGambarQr = data.actions[0].url
                                    val dataPaymentLink = data.actions[1].url
                                    val convertToInt = data.grossAmount.toDouble()
                                    val order = OrderEntity(
                                        total = convertToInt.toInt(),
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
        val idKantin = kantinSession.id.toInt()
        val tipePembayaran = "TUNAI"
        val statusPembayaran = "PAID"
        val uuidHolder = uuidToString
        cartViewModel.getSession().observe(this){ session ->
            cartViewModel.getTotalHarga().observe(this){ totalHarga ->
                cartViewModel.getAllCart().observe(this){ cartItem ->
                    val getTotalAmount = totalHarga?.toLong() ?: 0L
                    val email = session.email
                    val nama = session.nama_konsumen
                    cartItem.forEach {
                        val idMenu = it.idMenu ?: 0
                        val menuList = it.namaMenu.toString()
//                        val hargaMenu = it.harga ?: 0
                        val jumlahMenu = it.jumlah
                        cartViewModel.postDataTransaksi(uuidHolder,idKantin,getTotalAmount.toInt(),idMenu,menuList,jumlahMenu,tipePembayaran, statusPembayaran, email, nama)
                    }
                }
            }
        }
        Toast.makeText(this, "Pembayaran tunai sebesar ${convertToDouble}", Toast.LENGTH_SHORT).show()
    }

    private fun processNonCashPayment(kantinSession: KantinModel, convertToDouble: Long) {
        val nama = kantinSession.id
        val namaKantin = "Kantin"
        val email = kantinSession.email
        cartViewModel.postOrder(uuidToString,nama, namaKantin, email, convertToDouble)
    }

    private fun processNonCashPaymentTransaksi(kantinSession: KantinModel) {
        val idKantin = kantinSession.id.toInt()
        val tipePembayaran = "non-tunai"
        val statusPembayaran = "Pending"
        val uuidHolder = uuidToString
        cartViewModel.getSession().observe(this){ session ->
            cartViewModel.getTotalHarga().observe(this){ totalHarga ->
                cartViewModel.getAllCart().observe(this){ cartItem ->
                    val getTotalAmount = totalHarga?.toLong() ?: 0L
                    val email = session.email
                    val nama = session.nama_konsumen
                    cartItem.forEach {
                        val idMenu = it.idMenu ?: 0
                        val menuList = it.namaMenu.toString()
//                        val haragaMenu = it.harga ?: 0
                        val jumlahMenu = it.jumlah
                        cartViewModel.postDataTransaksi(uuidHolder,idKantin,getTotalAmount.toInt(),idMenu,menuList,jumlahMenu,tipePembayaran, statusPembayaran, email, nama)
                    }
                }
            }
        }
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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}