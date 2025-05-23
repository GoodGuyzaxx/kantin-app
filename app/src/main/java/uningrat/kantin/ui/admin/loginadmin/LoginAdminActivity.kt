package uningrat.kantin.ui.admin.loginadmin

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import uningrat.kantin.R
import uningrat.kantin.databinding.ActivityLoginAdminBinding
import uningrat.kantin.ui.ViewModelFactory
import uningrat.kantin.ui.admin.addnewkantin.AddNewKantinActivity
import uningrat.kantin.ui.admin.homeadmin.HomeAdminActivity
import uningrat.kantin.ui.admin.registeradmin.RegisterAdminActivity

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
        enableEdgeToEdge()
        singUpText()

        binding.btnLoginAsAdmin.setOnClickListener {
            isAllCheck = checkField()
            val email = binding.edtLoginEmail.text.toString()
            val password = binding.edtLoginPassword.text.toString()
            adminLoginViewModel.adminPostLogin(email,password)
        }
        adminLoginViewModel.adminResponse.observe(this){
            val adminId = it.data.id
            Log.d("TAG", "onCreate: $adminId")
            if (it.message == "Silakan Hubungi Admin Untuk Pendaftaran Kantin"){
                val i = Intent(this@LoginAdminActivity, AddNewKantinActivity::class.java)
                i.putExtra("id_admin", adminId)
                startActivity(i)
            } else if (it.success){
                val i = Intent(this@LoginAdminActivity, HomeAdminActivity::class.java)
                Toast.makeText(this@LoginAdminActivity, it.message, Toast.LENGTH_LONG).show()
                startActivity(i)
                finish()
            } else {
                Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
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

    private fun singUpText(){
        val text ="Daftar Menjadi Admin Kantin Sekarang!"
        val spanString = SpannableString(text)

        val clickAbleSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginAdminActivity, RegisterAdminActivity::class.java)
                widget.context.startActivity(intent)
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
                ds.color = getColor(R.color.primeblue)

            }
        }
        spanString.setSpan(clickAbleSpan,28,37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvSingup.text = spanString
        binding.tvSingup.highlightColor = Color.TRANSPARENT
        binding.tvSingup.movementMethod = LinkMovementMethod.getInstance()
    }
}