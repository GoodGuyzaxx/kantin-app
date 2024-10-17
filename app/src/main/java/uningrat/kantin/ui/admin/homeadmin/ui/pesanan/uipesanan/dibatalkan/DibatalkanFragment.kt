package uningrat.kantin.ui.admin.homeadmin.ui.pesanan.uipesanan.dibatalkan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import uningrat.kantin.adapter.PesananBatalAdapter
import uningrat.kantin.data.retrofit.response.DataTransaksi
import uningrat.kantin.databinding.FragmentDibatalkanBinding
import uningrat.kantin.ui.ViewModelFactory

class DibatalkanFragment : Fragment() {
    private var _binding : FragmentDibatalkanBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DibatalkanViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private val status = "Dibatalkan"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDibatalkanBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeDibatalkan.setOnRefreshListener {
            getData()
            binding.swipeDibatalkan.isRefreshing = false
        }

        getData()


    }

    private fun getData(){
        viewModel.getKantinSession().observe(viewLifecycleOwner){dataKantin ->
            val idKantin = dataKantin.id_konsumen
            viewModel.getDataTransaksiByStatus(idKantin, status)
        }

        viewModel.responseTransaksi.observe(viewLifecycleOwner){
            setUpRecyclerView(it.data)
        }
    }

    private fun setUpRecyclerView(data : List<DataTransaksi>) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvDibatalkan.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvDibatalkan.addItemDecoration(itemDecoration)

        val adapter = PesananBatalAdapter()
        adapter.submitList(data)
        binding.rvDibatalkan.adapter = adapter
    }


}