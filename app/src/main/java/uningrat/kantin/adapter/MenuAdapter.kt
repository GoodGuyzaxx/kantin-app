package uningrat.kantin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uningrat.kantin.R
import uningrat.kantin.data.local.entity.CartEntity
import uningrat.kantin.data.local.room.KantinDatabase
import uningrat.kantin.data.retrofit.response.MenuItem
import uningrat.kantin.databinding.ItemMenuBinding
import uningrat.kantin.ui.user.rating.RatingActivity

class MenuAdapter: ListAdapter<MenuItem, MenuAdapter.MenuViewHolder>(DIFF_CALLBACK) {

    class MenuViewHolder(private val binding: ItemMenuBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data : MenuItem){
            Glide
                .with(binding.root.context)
                .load(data.gambar)
                .into(binding.ivMenuItem)
            binding.tvDeskripsiItem.text = data.deskripsi
            binding.tvStockItem.text = itemView.context.getString(R.string.stok_menu, data.stock.toString())
            binding.tvNameMenuItem.text = data.namaMenu
            binding.tvHargaItem.text = itemView.context.getString(R.string.mata_uang, data.harga.toString())
            binding.btnRating.setOnClickListener {
                val i = Intent(binding.root.context, RatingActivity::class.java)
                i.putExtra("id", data.idMenu)
                binding.root.context.startActivity(i)

            }
            binding.btnTambah.setOnClickListener {
                insertIntoDatabase(data)
                binding.btnTambah.visibility = View.GONE
                binding.btnHapus.visibility = View.VISIBLE
            }
            binding.btnHapus.setOnClickListener {
                deleteFromDatabase(data.namaMenu)
                binding.btnTambah.visibility = View.VISIBLE
                binding.btnHapus.visibility = View.GONE
            }
        }

        private fun deleteFromDatabase(namaMenu: String) {
            val context = binding.root.context
            val database = KantinDatabase.getDatabase(context)
            CoroutineScope(Dispatchers.IO).launch {
                database.cartDao().deleteByNamaMenu(namaMenu)
            }
        }

        private fun insertIntoDatabase(data: MenuItem) {
            val context = binding.root.context
            val database = KantinDatabase.getDatabase(context)
            val cartItem = CartEntity(
                idMenu = data.idMenu,
                namaMenu = data.namaMenu,
                harga = data.harga,
                jumlah = 1
            )
                CoroutineScope(Dispatchers.IO).launch{
                    database.cartDao().insert(cartItem)
                }
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val list = getItem(position)
        holder.bind(list)
    }


    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MenuItem>(){
            override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
