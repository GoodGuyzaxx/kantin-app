package uningrat.kantin.ui.user.rating

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uningrat.kantin.R
import uningrat.kantin.databinding.ActivityRatingBinding
import uningrat.kantin.ui.ViewModelFactory

class RatingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRatingBinding
    private val viewModel by viewModels<RatingViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(ID_MENU)

        Log.d("TAG", "onCreate: $id")
    }

    companion object {
        private const val ID_MENU = "id"
    }
}