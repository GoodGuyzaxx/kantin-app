package uningrat.kantin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uningrat.kantin.data.retrofit.response.DataItem
import uningrat.kantin.databinding.ItemKantinBinding
import uningrat.kantin.ui.user.kantin.KantinActivity

class KantinAdapter: ListAdapter<DataItem, KantinAdapter.KantinViewHolder>(DIFF_CALLBACK) {

    class KantinViewHolder(private val binding: ItemKantinBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (data: DataItem) {
            binding.tvNamaItem.text = data.namaKantin
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KantinViewHolder {
        val binding = ItemKantinBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return KantinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KantinViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, KantinActivity::class.java)
            intentDetail.putExtra("id_kantin", item.id)
            intentDetail.putExtra("email_admin", item.adminEmail)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>(){
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}