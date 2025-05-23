package uningrat.kantin.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import uningrat.kantin.databinding.ActivityMainBinding
import uningrat.kantin.ui.admin.homeadmin.HomeAdminActivity
import uningrat.kantin.ui.admin.loginadmin.LoginAdminActivity
import uningrat.kantin.ui.user.Home.HomeActivity
import uningrat.kantin.ui.user.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        enableEdgeToEdge()

        binding.consumerButton.setOnClickListener{
            val i = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(i)
        }

        binding.ownerButton.setOnClickListener{
            val i = Intent(this@MainActivity, LoginAdminActivity::class.java)
            startActivity(i)
        }

        viewModel.getSession().observe(this){
            if (it.status == "user"){
                val i = Intent(this@MainActivity, HomeAdminActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
                finish()

            }else if (it.status == "konsumen") {
                val i = Intent(this, HomeActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
                finish()
            }
        }

    }
}