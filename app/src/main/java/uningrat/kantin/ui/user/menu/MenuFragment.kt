package uningrat.kantin.ui.user.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uningrat.kantin.adapter.MenuAdapter
import uningrat.kantin.data.retrofit.response.MenuItem
import uningrat.kantin.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)

        binding.rvFragment.layoutManager = LinearLayoutManager(requireActivity())

        arguments?.let {
            val positin = it.getInt(ARG_POSITION)
            val idKantinValue = it.getString(ARG_ID).toString()
            if (positin == 1){
                viewModel.listMakanan.observe(viewLifecycleOwner){
                    setUpRecycleView(it)

                }
                viewModel.getMenuMakanan(idKantinValue)
            }else {
                viewModel.listMinuman.observe(viewLifecycleOwner){
                    setUpRecycleView(it)
                }
                viewModel.getMenuMinuman(idKantinValue)
            }
        }
    }

    private fun setUpRecycleView(data : List<MenuItem>){
        val adapater = MenuAdapter()
        adapater.submitList(data)
        binding.rvFragment.adapter = adapater
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_ID = "idKantin"
    }

}