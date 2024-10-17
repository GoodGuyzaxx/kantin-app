package uningrat.kantin.ui.user.transaksi

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import uningrat.kantin.adapter.HistoryAdapter
import uningrat.kantin.data.retrofit.response.DataTransaksi
import uningrat.kantin.databinding.ActivityTransaksiBinding
import uningrat.kantin.ui.ViewModelFactory

class TransaksiActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTransaksiBinding
    private val viewModel by viewModels<TransaksiViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = "Riwayat"
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        viewModel.getSession().observe(this){session ->
            val email = session.email
            viewModel.getDataTransaksi(email)
        }

        viewModel.responseTransaksi.observe(this){
            setUpRecyclerView(it.data)
        }

        binding.swipeRiwayat.setOnRefreshListener {
            viewModel.getSession().observe(this){session ->
                val email = session.email
                viewModel.getDataTransaksi(email)
            }
            binding.swipeRiwayat.isRefreshing = false
        }

    }

    private fun setUpRecyclerView(data : List<DataTransaksi>) {
        val layoutManager = LinearLayoutManager(this)
        binding.rvTransaksi.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvTransaksi.addItemDecoration(itemDecoration)

        val adapter = HistoryAdapter()
        adapter.submitList(data)
        binding.rvTransaksi.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}