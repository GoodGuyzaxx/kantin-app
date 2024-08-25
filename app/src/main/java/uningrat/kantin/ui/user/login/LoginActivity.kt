package uningrat.kantin.ui.user.login

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import uningrat.kantin.R
import uningrat.kantin.databinding.ActivityLoginBinding
import uningrat.kantin.ui.user.Home.HomeActivity
import uningrat.kantin.ui.ViewModelFactory
import uningrat.kantin.ui.admin.loginadmin.LoginAdminActivity
import uningrat.kantin.ui.user.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        singUpText()
        binding.btnLoginAsAdmin.setOnClickListener {
            val i = Intent(this@LoginActivity, LoginAdminActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(i)
            finish()
        }

        lifecycleScope.launch {
            loginViewModel.toastEvent.collect {
                showToast(it)
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtLoginEmail.text.toString()
            val password = binding.edtLoginPassword.text.toString()
            loginViewModel.getLogin(email, password)
        }

        loginViewModel.loginResponse.observe(this){
            if (!it.success){
                showToast(it.message)
            } else {
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                showToast("Login ${it.message}")
                finish()
            }
        }
    }

    private fun showToast(text : String) {
        Toast.makeText(this,text,Toast.LENGTH_LONG).show()
    }

    private fun singUpText(){
        val text ="Belum punya akun? Daftar Sekarang!"
        val spanString = SpannableString(text)

        val clickAbleSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                widget.context.startActivity(intent)
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
                ds.color = getColor(R.color.primeblue)

            }
        }
        spanString.setSpan(clickAbleSpan,18,34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvSingup.text = spanString
        binding.tvSingup.highlightColor = Color.TRANSPARENT
        binding.tvSingup.movementMethod = LinkMovementMethod.getInstance()
    }



}