package uningrat.kantin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uningrat.kantin.ui.admin.homeadmin.ui.pesanan.uipesanan.dibatalkan.DibatalkanFragment
import uningrat.kantin.ui.admin.homeadmin.ui.pesanan.uipesanan.diproses.DiprosesFragment
import uningrat.kantin.ui.admin.homeadmin.ui.pesanan.uipesanan.diterima.DiterimaFragment

class PesananPagerAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0-> DiterimaFragment()
            1-> DiprosesFragment()
            2-> DibatalkanFragment()
            else -> DiterimaFragment()
        }
    }
}