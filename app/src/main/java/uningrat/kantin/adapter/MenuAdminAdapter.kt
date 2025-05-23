package uningrat.kantin.adapter

import android.app.AlertDialog
import android.content.Intent
import android.util.Log.e
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uningrat.kantin.R
import uningrat.kantin.data.retrofit.ApiConfig
import uningrat.kantin.data.retrofit.response.MenuItem
import uningrat.kantin.data.retrofit.response.MenuResponse
import uningrat.kantin.databinding.ItemAdminMenuBinding
import uningrat.kantin.ui.admin.editmenu.EditMenuActivity
import uningrat.kantin.ui.admin.homeadmin.ui.ratingdetail.RatingDetailActivity

class MenuAdminAdapter : ListAdapter <MenuItem, MenuAdminAdapter.MenuViewHolder>(DIFF_CALLBACK) {


    inner class MenuViewHolder(private val binding: ItemAdminMenuBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data : MenuItem){
            Glide
                .with(binding.root.context)
                .load(data.gambar)
                .into(binding.ivMenuItem)
            binding.tvNameMenuItemAdmin.text = data.namaMenu
            binding.tvHargaItemAdmin.text = itemView.context.getString(R.string.mata_uang, data.harga)
            binding.ivDeleteMenuAdmin.setOnClickListener{
                showDeleteConfirmationDialog(data, data.idMenu )
            }
            binding.ivEditMenuAdmin.setOnClickListener {
                val i = Intent(binding.root.context, EditMenuActivity::class.java)
                i.putExtra("id", data.idMenu.toString())
                i.putExtra("nama", data.namaMenu)
                i.putExtra("harga", data.harga.toString())
                i.putExtra("gambar", data.gambar)
                i.putExtra("kategori", data.kategori)
                i.putExtra("deskripsi", data.deskripsi)
                i.putExtra("stok", data.stock.toString())
                binding.root.context.startActivity(i)
            }
            binding.layoutRatingClickable.setOnClickListener{
                val i = Intent(binding.root.context, RatingDetailActivity::class.java)
                i.putExtra("id", data.idMenu.toString())
                binding.root.context.startActivity(i)
            }
        }

        private fun showDeleteConfirmationDialog(menuItem: MenuItem, id: Int) {
            val context = binding.root.context
            AlertDialog.Builder(context)
                .setTitle("Konfirmasi Hapus")
                .setMessage("Apakah Anda yakin ingin menghapus ${menuItem.namaMenu}?")
                .setPositiveButton("Ya") { _, _ ->
                    deleteMenu(id.toString())
                }
                .setNegativeButton("Tidak", null)
                .show()
        }

        private val _responseMenu = MutableLiveData<MenuResponse>()
        private fun deleteMenu(id: String) {
            val context = binding.root.context
            val client = ApiConfig.getApiService().deleteMenuByKantinId(id)
            client.enqueue(object : Callback<MenuResponse>{
                override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                    if (response.isSuccessful){
                        Toast.makeText(context, "Menu berhasil dihapus silakan refresh", Toast.LENGTH_SHORT).show()
                        _responseMenu.value = response.body()
                    }else {
                        Toast.makeText(context, "Gagal menghapus menu", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(p0: Call<MenuResponse>, p1: Throwable) {
                    e("TAG", "onFailure: ${p1.message.toString()}", )
                }
            })
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemAdminMenuBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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