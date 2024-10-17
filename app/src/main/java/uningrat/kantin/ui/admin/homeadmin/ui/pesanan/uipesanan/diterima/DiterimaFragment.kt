package uningrat.kantin.ui.admin.homeadmin.ui.pesanan.uipesanan.diterima

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import uningrat.kantin.adapter.PesananAdapter
import uningrat.kantin.data.retrofit.response.DataTransaksi
import uningrat.kantin.databinding.FragmentDiterimaBinding
import uningrat.kantin.ui.ViewModelFactory


class DiterimaFragment : Fragment(), PesananAdapter.OnPesananStatusClickListener {
    private var _binding: FragmentDiterimaBinding? =null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DiterimaViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private val defaultStatus = "Diterima"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDiterimaBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeDiterima.setOnRefreshListener {
            viewModel.getKantinSession().observe(viewLifecycleOwner) { dataKantin ->
                val idKanitn = dataKantin.id_konsumen
                viewModel.getDataTransaksiByStatus(idKanitn,defaultStatus)
            }
            binding.swipeDiterima.isRefreshing = false
        }

        viewModel.getKantinSession().observe(viewLifecycleOwner) { dataKantin ->
            val idKanitn = dataKantin.id_konsumen
            viewModel.getDataTransaksiByStatus(idKanitn,defaultStatus)
        }

        viewModel.responseTransaksi.observe(viewLifecycleOwner){
            setUpRecyclerViewDiterima(it.data)
        }

    }

    private fun setUpRecyclerViewDiterima(data : List<DataTransaksi>) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvDiterima.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvDiterima.addItemDecoration(itemDecoration)

        val adapter = PesananAdapter(this@DiterimaFragment)
        adapter.submitList(data)
        binding.rvDiterima.adapter = adapter
    }

    override fun onPesananStatusClick(data: DataTransaksi) {
        val idOrder = data.idOrder
        viewModel.updateDataTransaksi(idOrder,"Diproses")

        viewModel.getKantinSession().observe(viewLifecycleOwner) { dataKantin ->
            val idKanitn = dataKantin.id_konsumen
            viewModel.getDataTransaksiByStatus(idKanitn,defaultStatus)
        }
    }

    override fun onUpdateStatus(data: DataTransaksi) {
        val idOrder = data.idOrder
        viewModel.updateDataTransaksi(idOrder,"Dibatalkan")

        viewModel.getKantinSession().observe(viewLifecycleOwner) { dataKantin ->
            val idKanitn = dataKantin.id_konsumen
            viewModel.getDataTransaksiByStatus(idKanitn,defaultStatus)
        }
    }


}