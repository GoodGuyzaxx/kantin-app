package uningrat.kantin.ui.user.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import uningrat.kantin.databinding.ActivityProfileBinding
import uningrat.kantin.ui.ViewModelFactory
import uningrat.kantin.ui.user.login.LoginActivity
import uningrat.kantin.ui.user.updateprofile.UpdateProifleActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val profileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = "Profile"
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        binding.btnAkun.setOnClickListener {
            val i = Intent(this@ProfileActivity, UpdateProifleActivity::class.java)
            startActivity(i)
        }

        profileViewModel.getSession().observe(this){
            binding.tvProfileName.text = it.nama_konsumen
            binding.tvProfileNotelp.text = it.no_telp
            binding.tvProfileEmail.text = it.email
        }

        binding.btnKeluar.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(i)
            profileViewModel.logout()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}