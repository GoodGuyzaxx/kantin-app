package uningrat.kantin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import uningrat.kantin.ui.user.Home.HomeActivity
import uningrat.kantin.ui.user.kantin.KantinActivity
import uningrat.kantin.ui.user.login.LoginActivity
import uningrat.kantin.ui.user.register.RegisterActivity

class MainActivity : AppCompatActivity() {
    private lateinit var buttnMove : Button
    private lateinit var buttnMove1 : Button
    private lateinit var buttnMove2 : Button
    private lateinit var buttnMove3 : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttnMove = findViewById(R.id.btn_move)
        buttnMove.setOnClickListener {
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
        }

        buttnMove1 = findViewById(R.id.btn_move1)
        buttnMove1.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }

        buttnMove2 = findViewById(R.id.btn_move2)
        buttnMove2.setOnClickListener {
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
        }

        buttnMove3 = findViewById(R.id.btn_move3)
        buttnMove3.setOnClickListener {
            val i = Intent(this, KantinActivity::class.java)
            startActivity(i)
        }

    }
}