package uningrat.kantin.ui.user.Home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import uningrat.kantin.R
import uningrat.kantin.adapter.KantinAdapter
import uningrat.kantin.data.retrofit.response.DataItem
import uningrat.kantin.databinding.ActivityHomeBinding
import uningrat.kantin.ui.ViewModelFactory
import uningrat.kantin.ui.user.cart.CartActivity
import uningrat.kantin.ui.user.order.OrderActivity
import uningrat.kantin.ui.user.profile.ProfileActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private val homeViewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val roteteOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val roteteClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }

    private var clicked = false


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabOpen.setColorFilter(Color.WHITE)
        binding.fabOrder.setColorFilter(Color.WHITE)
        binding.fabCart.setColorFilter(Color.WHITE)


        setUpRecycleView()

        homeViewModel.kantinResponse.observe(this){
            setDataKantin(it)
        }


        homeViewModel.getSession().observe(this){ data ->
            binding.tvHomeName.text = "Hi,${data.nama_konsumen}"
        }



        binding.ivHomeProfie.setOnClickListener {
            val i = Intent(this@HomeActivity, ProfileActivity::class.java)
            startActivity(i)
        }

        binding.fabOpen.setOnClickListener {
            onAddButtonClicked()
        }

        binding.fabOrder.setOnClickListener{
            startActivity(Intent(this, OrderActivity::class.java))
        }

        binding.fabCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }


    }


    private fun onAddButtonClicked(){
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setVisibility(click: Boolean){
        if (!click){
            binding.fabOrder.visibility = View.VISIBLE
            binding.fabCart.visibility = View.VISIBLE
        }else{
            binding.fabOrder.visibility = View.INVISIBLE
            binding.fabCart.visibility = View.INVISIBLE
    }
    }

    private fun setAnimation(click: Boolean){
        if (!click) {
            binding.fabOpen.startAnimation(roteteOpen)
            binding.fabCart.startAnimation(fromBottom)
            binding.fabOrder.startAnimation(fromBottom)
        }else {
            binding.fabOpen.startAnimation(roteteClose)
            binding.fabCart.startAnimation(toBottom)
            binding.fabOrder.startAnimation(toBottom)
        }
    }

    private fun setUpRecycleView(){
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.rvKantinHome.layoutManager =layoutManager
    }

    private fun setDataKantin(data: List<DataItem>){
        val adapter = KantinAdapter()
        adapter.submitList(data)
        binding.rvKantinHome.adapter = adapter
    }
}