package uningrat.kantin.ui.admin.loginadmin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import uningrat.kantin.databinding.ActivityLoginAdminBinding
import uningrat.kantin.ui.ViewModelFactory
import uningrat.kantin.ui.admin.homeadmin.HomeAdminActivity

class LoginAdminActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginAdminBinding
    private val adminLoginViewModel by viewModels<LoginAdminViewModel> {
        ViewModelFactory.getInstance(this)
    }
    var isAllCheck = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoginAsAdmin.setOnClickListener {
            isAllCheck = checkField()
            val email = binding.edtLoginEmail.text.toString()
            val password = binding.edtLoginPassword.text.toString()
            adminLoginViewModel.adminPostLogin(email,password)
        }
        adminLoginViewModel.adminResponse.observe(this){
            if (!it.success){
                Toast.makeText(this@LoginAdminActivity, it.message, Toast.LENGTH_LONG).show()
            } else {
                val i = Intent(this@LoginAdminActivity, HomeAdminActivity::class.java)
                Toast.makeText(this@LoginAdminActivity, it.message, Toast.LENGTH_LONG).show()
                startActivity(i)
                finish()
            }
        }
    }


    private fun checkField(): Boolean {
        if (binding.edtLoginEmail.length() == 0){
            binding.edtLoginEmail.error = "Field ini tidak boleh kosong"
            return false
        }
        if (binding.edtLoginPassword.length() == 0){
            binding.edtLoginEmail.error = "Field ini tidak boleh kosong"
            return false
        }
        return true
    }
}