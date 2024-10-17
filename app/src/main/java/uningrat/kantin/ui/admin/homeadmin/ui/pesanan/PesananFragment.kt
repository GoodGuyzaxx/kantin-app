package uningrat.kantin.ui.admin.homeadmin.ui.pesanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import uningrat.kantin.adapter.PesananPagerAdapter
import uningrat.kantin.databinding.FragmentPesananBinding

class PesananFragment : Fragment() {
    private lateinit var viewPagerAdapter: PesananPagerAdapter
    private var _binding: FragmentPesananBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPesananBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        viewPagerAdapter = PesananPagerAdapter(requireActivity())
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Diterima"
                1 -> tab.text = "Diproses"
                2 -> tab.text = "Dibatalkan"
            }

        }.attach()

    }

    companion object {

    }
}