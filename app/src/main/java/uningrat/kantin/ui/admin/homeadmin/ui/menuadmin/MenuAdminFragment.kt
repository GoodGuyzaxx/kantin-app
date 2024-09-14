package uningrat.kantin.ui.admin.homeadmin.ui.menuadmin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import uningrat.kantin.adapter.MenuAdminAdapter
import uningrat.kantin.data.retrofit.response.MenuItem
import uningrat.kantin.databinding.FragmentMenuAdminBinding
import uningrat.kantin.ui.ViewModelFactory
import uningrat.kantin.ui.admin.addmenu.AddMenuActivity

class MenuAdminFragment : Fragment() {
    private var _binding: FragmentMenuAdminBinding? =null
    private val binding get() = _binding!!
    private val viewModel: MenuAdminViewModel by viewModels{
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMenuAdminBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAddMenuAdmin.setColorFilter(Color.WHITE)
        binding.fabAddMenuAdmin.setOnClickListener{
            val i = Intent(requireContext(), AddMenuActivity::class.java)
            startActivity(i)
        }

        binding.swipeMenuAdmin.setOnRefreshListener {
            getResponseMenu()
            binding.swipeMenuAdmin.isRefreshing = false
        }

        getResponseMenu()

        binding.rvMenuAdmin.layoutManager = GridLayoutManager(requireContext(),2)

    }

    private fun getResponseMenu(){
        viewModel.getSession().observe(viewLifecycleOwner){
            viewModel.getAllMenuByKantin(it.id_konsumen)
        }

        viewModel.responseMenu.observe(viewLifecycleOwner){
            setUpRecycleView(it.data)
        }
    }

    private fun setUpRecycleView(data: List<MenuItem>) {
        val adapter = MenuAdminAdapter()
        adapter.submitList(data)
        binding.rvMenuAdmin.adapter = adapter
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}