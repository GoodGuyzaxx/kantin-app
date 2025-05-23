package uningrat.kantin.ui.admin.homeadmin.ui.riwayatadmin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import uningrat.kantin.R
import uningrat.kantin.adapter.PesananBatalAdapter
import uningrat.kantin.data.retrofit.response.DataTransaksi
import uningrat.kantin.databinding.ActivityRiwayatAdminBinding
import uningrat.kantin.helper.MonthYearPickerDialog
import uningrat.kantin.ui.ViewModelFactory
import uningrat.kantin.ui.admin.homeadmin.HomeAdminActivity

class RiwayatAdminActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRiwayatAdminBinding
    private val viewModel by viewModels<RiwayatAdminViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val status = "Selesai"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = "Riwayat"
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        val idKantin = intent.getStringExtra(ID_KANTIN)

        viewModel.getPenghasilanKantin(idKantin.toString())
        viewModel.kantinResponse.observe(this){
            val defaultDataUang = 0
            if (!it.success) {
                binding.tvPenghasilan.text = resources.getString(R.string.mata_uang, defaultDataUang.toString())
            } else {
                binding.tvPenghasilan.text = resources.getString(R.string.mata_uang, it.data.totalHargaKantin.toString())
            }
        }

        viewModel.getDataTransaksiByStatus(idKantin.toString(),status)
        viewModel.responseTransaksi.observe(this){
            setUpRecyclerView(it.data)
        }

        binding.fabFilterOrder.setOnClickListener {
            showMonthYearPicker()
        }

    }

    private fun setUpRecyclerView(data : List<DataTransaksi>) {
        val layoutManager = LinearLayoutManager(this)
        binding.rvRiwayat.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvRiwayat.addItemDecoration(itemDecoration)

        val adapter = PesananBatalAdapter()
        adapter.submitList(data)
        binding.rvRiwayat.adapter = adapter
    }

    fun showMonthYearPicker() {
        MonthYearPickerDialog(
            context = this,
            onDateSelected = { month, year ->
                // Handle the selected date
                val selectedMonth = month + 1 // Adding 1 since months are 0-based
                Log.d("DatePicker", "Selected: Month: $selectedMonth, Year: $year")
                // Update your UI here
                val idKantin = intent.getStringExtra(ID_KANTIN).toString()
                viewModel.getTransaksiByMonth(idKantin,selectedMonth,year)
                viewModel.transaksiByMonth.observe(this){
                    val defaultDataUang = 0
                    if (it.data.totalAmount == 0) {
                        binding.tvPenghasilan.text = resources.getString(R.string.mata_uang, defaultDataUang.toString())
                    } else {
                        binding.tvPenghasilan.text = resources.getString(R.string.mata_uang, it.data.totalAmount.toString())
                        val valueChaek = it.data.totalAmount
                        Log.e("TAG", "showMonthYearPicker: $valueChaek", )
                    }
                }
            }
        ).show(supportFragmentManager, "MonthYearPickerDialog")
    }



    companion object {
        private const val ID_KANTIN = "id"
    }

    override fun onSupportNavigateUp(): Boolean {
        val i = Intent(this@RiwayatAdminActivity, HomeAdminActivity::class.java)
        startActivity(i)
        finish()
        return true
    }
}