package uningrat.kantin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uningrat.kantin.data.retrofit.response.DetailMenuItem
import uningrat.kantin.databinding.ItemMenuOrderBinding

class PesananDetailAdapter: ListAdapter<DetailMenuItem, PesananDetailAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: ItemMenuOrderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: DetailMenuItem) {
            binding.menuName.text = data.nama
            binding.menuQuantity.text = data.jumlah.toString()

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PesananDetailAdapter.ViewHolder {
        val binding = ItemMenuOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder:PesananDetailAdapter.ViewHolder, position: Int) {
        val list = getItem(position)
        holder.bind(list)
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailMenuItem>(){
            override fun areItemsTheSame(oldItem: DetailMenuItem, newItem: DetailMenuItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: DetailMenuItem, newItem: DetailMenuItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}