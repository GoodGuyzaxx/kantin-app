package uningrat.kantin.ui.admin.registeradmin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uningrat.kantin.databinding.ActivityRegisterAdminBinding
import uningrat.kantin.ui.ViewModelFactory
import uningrat.kantin.ui.admin.addnewkantin.AddNewKantinActivity
import uningrat.kantin.ui.admin.loginadmin.LoginAdminActivity

class RegisterAdminActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterAdminBinding
    private val viewModel by viewModels<RegisterAdminViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnRegister.setOnClickListener{
            getRegister()
        }

//        binding.btnRegiterKantin.setOnClickListener {
//            val i = Intent(this@RegisterAdminActivity, AddNewKantinActivity::class.java)
//            startActivity(i)
//        }

        viewModel.registerResponse.observe(this) {
            if (!it.success) {
                showToast(it.message)
            } else {
                val idAdmin = it.data.idAdmin
                showToast(it.message)
                lifecycleScope.launch {
                    delay(1000)
                }
                val i = Intent(this, AddNewKantinActivity::class.java)
                i.putExtra("id_admin", idAdmin)
                startActivity(i)
                finish()
            }
        }
    }

    fun getRegister(){
        val nama_admin = binding.edtRegisterName.text.toString()
        val email_admin = binding.edtRegisterEmail.text.toString()
        val no_telp = binding.edtRegisterTelp.text.toString()
        val password_admin = binding.edtRegisterPassword.text.toString()

        viewModel.registerAdmin(nama_admin, email_admin, no_telp, password_admin)
    }


    fun showToast(message: String){
        Toast.makeText(this, message,Toast.LENGTH_LONG).show()
    }
}