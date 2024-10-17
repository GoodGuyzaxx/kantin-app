package uningrat.kantin.ui.user.rating

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

        val actionBar = supportActionBar
        actionBar?.title = "Rating"
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        val getRating = binding.ratingBar.rating
        val id = intent.getIntExtra(ID_MENU,0)
        Log.d("TAG", "onCreate: $getRating")


        viewModel.getSession().observe(this) { session ->
            val idKonsumen = session.id_konsumen.toInt()
            viewModel.getRatingUser(idKonsumen, id)

            viewModel.ratingUserResponse.observe(this) { response ->
                if (response.success) {
                    val existingRating = response.data.first().rating
                    binding.ratingBar.rating = existingRating.toFloat()

                    binding.updateButton.visibility = View.VISIBLE
                    binding.btnSubmit.visibility = View.GONE

                    binding.updateButton.setOnClickListener {
                        val newRating = binding.ratingBar.rating.toInt()
                        if (newRating != existingRating) {
                            viewModel.updateRatingUser(idKonsumen, id, newRating)
                            Toast.makeText(this, "Rating updated to $newRating", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Rating unchanged", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    binding.btnSubmit.visibility = View.VISIBLE
                    binding.updateButton.visibility = View.GONE
                    binding.btnSubmit.setOnClickListener {
                        val ratingValue = binding.ratingBar.rating.toInt()
                        viewModel.postRating(idKonsumen, id, ratingValue)
                        Toast.makeText(this, "New rating $ratingValue submitted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        private const val ID_MENU = "id"
    }
}