package uningrat.kantin.ui.user.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import uningrat.kantin.databinding.ActivityRegisterBinding
import uningrat.kantin.ui.ViewModelFactory
import uningrat.kantin.ui.user.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            registerViewModel.toastEvent.collect {
                showToast(it)
            }
        }

        binding.btnRegister.setOnClickListener {
            val namaKonsumen = binding.edtRegisterName.text.toString()
            val emailKonsumen = binding.edtRegisterEmail.text.toString()
            val noTelpKonsumen = binding.edtRegisterTelp.text.toString()
            val passwordKonsumen = binding.edtRegisterPassword.text.toString()
            registerViewModel.getRegister(namaKonsumen,emailKonsumen,noTelpKonsumen,passwordKonsumen)
        }

        registerViewModel.registerResponse.observe(this){
            showToast(it.message)
            if (it.success){
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
                finish()
            }
        }
    }

    private fun showToast(text : String){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
    }
}