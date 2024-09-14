package uningrat.kantin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uningrat.kantin.R
import uningrat.kantin.data.local.entity.CartEntity
import uningrat.kantin.data.local.room.KantinDatabase
import uningrat.kantin.databinding.ItemCartBinding

class CartAdapter: ListAdapter<CartEntity, CartAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(private val binding: ItemCartBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CartEntity) {
            binding.tvCartNamaMenu.text = data.namaMenu
            binding.tvCartHarga.text =  itemView.context.getString(R.string.mata_uang, data.harga)
            binding.tvCartJumlah.text = data.jumlah.toString()
            binding.btnAddCart.setOnClickListener {
                updateJumlahItem(data.id,data.jumlah.plus(1))
            }
            binding.btnRemoveCart.setOnClickListener {
                if (data.jumlah.equals(1) ) {
                    deleteFromDatabase(data)
                } else {
                    updateJumlahItem(data.id,data.jumlah.minus(1))
                }
            }

        }

        private fun updateJumlahItem(cartItemid: Int, newJumlah: Int){
            val context = binding.root.context
            val database = KantinDatabase.getDatabase(context)
            CoroutineScope(Dispatchers.IO).launch {
                database.cartDao().updateJumlah(cartItemid,newJumlah)
            }
        }

        private fun deleteFromDatabase(cartEntity: CartEntity) {
            val context = binding.root.context
            val database = KantinDatabase.getDatabase(context)
            CoroutineScope(Dispatchers.IO).launch {
                database.cartDao().delete(cartEntity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CartEntity>(){
            override fun areItemsTheSame(
                oldItem: CartEntity,
                newItem: CartEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CartEntity,
                newItem: CartEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}