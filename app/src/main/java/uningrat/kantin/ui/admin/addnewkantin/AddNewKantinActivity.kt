package uningrat.kantin.ui.admin.addnewkantin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uningrat.kantin.R
import uningrat.kantin.databinding.ActivityAddNewKantinBinding
import uningrat.kantin.ui.ViewModelFactory
import uningrat.kantin.ui.admin.loginadmin.LoginAdminActivity
import uningrat.kantin.ui.admin.registeradmin.RegisterAdminActivity

class AddNewKantinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewKantinBinding
    private val viewModel by viewModels<AddNewKantinViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding  = ActivityAddNewKantinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idAdmin = intent.getIntExtra(ID_ADMIN , 0)


        binding.btnLoginAsAdmin.setOnClickListener {
            val namaKantin = binding.edtLoginEmail.text.toString()
            if (namaKantin.isEmpty()) {
                Toast.makeText(this,"Field tidak boleh Kosong", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.postRegisterAdminKantin(namaKantin ,idAdmin)
            }
            viewModel.registerAdminResponse.observe(this){
                if (!it.success) {
                    startActivity(Intent(this@AddNewKantinActivity, RegisterAdminActivity::class.java))
                } else {
                    val i = Intent(this@AddNewKantinActivity , LoginAdminActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }
        }

    }


    companion object {
        const val ID_ADMIN = "id_admin"
    }

}