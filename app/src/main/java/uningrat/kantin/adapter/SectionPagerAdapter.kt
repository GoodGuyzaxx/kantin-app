package uningrat.kantin.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import uningrat.kantin.ui.user.menu.MenuFragment

class SectionPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    var idKantin: String = "id_user"
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = MenuFragment()
        fragment.arguments = Bundle().apply {
            putInt(MenuFragment.ARG_POSITION, position + 1)
            putString(MenuFragment.ARG_ID, idKantin)
        }
        return fragment
    }
}