package uningrat.kantin.ui.admin.homeadmin.ui.ratingdetail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import uningrat.kantin.adapter.RatingDetailAdapter
import uningrat.kantin.data.retrofit.response.DataRatingList
import uningrat.kantin.databinding.ActivityRatingDetailBinding
import uningrat.kantin.ui.ViewModelFactory

class RatingDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRatingDetailBinding
    private val viewmodel by viewModels<RatingDetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = "List Rating"
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        val menuID = intent.getStringExtra(EXTRA_ID).toString()

        val layoutManager = LinearLayoutManager(this)
        binding.ratingDetainRv.layoutManager = layoutManager

        viewmodel.getRatingByMenu(menuID)

        viewmodel.ratingResponse.observe(this){
            setUpRecyclerView(it.data)
        }

    }

    private fun setUpRecyclerView(data : List<DataRatingList>) {
        val adapter = RatingDetailAdapter()
        adapter.submitList(data)
        binding.ratingDetainRv.adapter = adapter
    }

    companion object {
        const val EXTRA_ID = "id"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}