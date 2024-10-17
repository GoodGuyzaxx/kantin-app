package uningrat.kantin.ui.user.kantin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uningrat.kantin.R
import uningrat.kantin.adapter.SectionPagerAdapter
import uningrat.kantin.data.pref.KantinModel
import uningrat.kantin.databinding.ActivityKantinBinding
import uningrat.kantin.ui.ViewModelFactory
import uningrat.kantin.ui.user.cart.CartActivity

class KantinActivity : AppCompatActivity() {
    private lateinit var binding : ActivityKantinBinding
    private val kantinViewModel by viewModels<KantinViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKantinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)


        val getIdKantin = intent.getIntExtra(ID_KANTIN,0)
        val getEmail = intent.getStringExtra(EMAIL_ADMIN)
        actionBar?.title = "Kantin $getIdKantin"

        kantinViewModel.saveKantinSession(KantinModel(
            id = getIdKantin.toString(),
            email = getEmail.toString(),
            nama_kantin = "Kantin"
        ))

        binding.fabCart.setColorFilter(Color.WHITE)
        binding.fabCart.setOnClickListener{
            val intent = Intent(this, CartActivity::class.java)
            intent.putExtra("email_admin", getEmail)
            intent.putExtra("id_kantin", getIdKantin)
            startActivity(intent)
            Log.d("TAG", "onCreate: $getEmail $getIdKantin")
        }

        //ViewPager
        val sectionPagerAdapter = SectionPagerAdapter(this)
            sectionPagerAdapter.idKantin = getIdKantin.toString()


        binding.viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs,binding.viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        private val ID_KANTIN = "id_kantin"
        private val EMAIL_ADMIN = "email_admin"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.makanan,
            R.string.minuman
        )
    }
}