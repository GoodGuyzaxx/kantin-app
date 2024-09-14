package uningrat.kantin.ui.user.updateprofile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import uningrat.kantin.databinding.ActivityUpdateProifleBinding
import uningrat.kantin.ui.ViewModelFactory

class UpdateProifleActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUpdateProifleBinding
    private val updateViewModel by viewModels<UpdateProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProifleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = "Akun"
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        binding.btnUpdateProfile.setOnClickListener {
            updateViewModel.getSession().observe(this){
                val getIdKonsumen = it.id_konsumen
                val email = binding.edtUpdateEmail.text.toString()
                val noTelp = binding.edtUpdateNoTelp.text.toString()
                updateViewModel.updateProfile(getIdKonsumen,email,noTelp)
            }
        }

        updateViewModel.updateProfileResponse.observe(this){
            Toast.makeText(this, "Mantap, ${it.message}", Toast.LENGTH_LONG).show()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}