package uningrat.kantin.ui.admin.homeadmin.ui.pesanan.uipesanan.diproses

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
import uningrat.kantin.databinding.FragmentDiprosesBinding
import uningrat.kantin.ui.ViewModelFactory

class DiprosesFragment : Fragment(), PesananAdapter.OnPesananStatusClickListener {
    private var _binding : FragmentDiprosesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DiprosesViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private val defaultStatus = "Diproses"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDiprosesBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeDiproses.setOnRefreshListener {
            viewModel.getKantinSession().observe(viewLifecycleOwner) { dataKantin ->
                val idKanitn = dataKantin.id_konsumen
                viewModel.getDataTransaksiByStatus(idKanitn, defaultStatus)
            }
            binding.swipeDiproses.isRefreshing = false
        }

        viewModel.getKantinSession().observe(viewLifecycleOwner){dataKantin ->
            val idKanitn = dataKantin.id_konsumen
            viewModel.getDataTransaksiByStatus(idKanitn, defaultStatus)
        }

        viewModel.responseTransaksi.observe(viewLifecycleOwner){
            setUpRecyclerView(it.data)
        }

    }

    private fun setUpRecyclerView(data : List<DataTransaksi>) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvDiproses.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvDiproses.addItemDecoration(itemDecoration)

        val adapter = PesananAdapter(this@DiprosesFragment)
        adapter.submitList(data)
        binding.rvDiproses.adapter = adapter
    }

    override fun onPesananStatusClick(data: DataTransaksi) {
        val idTransaksi = data.idOrder
        viewModel.updateDataTransaksi(idTransaksi, "Selesai")
        viewModel.getKantinSession().observe(viewLifecycleOwner){dataKantin ->
            val idKanitn = dataKantin.id_konsumen
            viewModel.getDataTransaksiByStatus(idKanitn, defaultStatus)
        }
    }

    override fun onUpdateStatus(data: DataTransaksi) {
        val idTransaksi = data.idOrder
        viewModel.updateDataTransaksi(idTransaksi, "Dibatalkan")
        viewModel.getKantinSession().observe(viewLifecycleOwner){dataKantin ->
            val idKanitn = dataKantin.id_konsumen
            viewModel.getDataTransaksiByStatus(idKanitn, defaultStatus)
        }
    }


}