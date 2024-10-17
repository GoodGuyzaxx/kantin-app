package uningrat.kantin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uningrat.kantin.R
import uningrat.kantin.data.retrofit.response.DataRekomendasiMenu
import uningrat.kantin.databinding.ItemRekomendasiMenuBinding

class RekomendasiAdapter: ListAdapter<DataRekomendasiMenu, RekomendasiAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: ItemRekomendasiMenuBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataRekomendasiMenu) {
            Glide
                .with(binding.root.context)
                .load(data.gambar)
                .into(binding.ivImageMenu)
            binding.textNamaMenu.text = data.nama
            binding.textNamaKantin.text = data.namaKantin
            binding.textHargaMenu.text = itemView.context.getString(R.string.mata_uang, data.harga.toString())
            binding.textRatingMenu.text = data.rating
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RekomendasiAdapter.ViewHolder {
        val binding = ItemRekomendasiMenuBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RekomendasiAdapter.ViewHolder, position: Int) {
        val list = getItem(position)
        holder.bind(list)
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataRekomendasiMenu>(){
            override fun areItemsTheSame(oldItem: DataRekomendasiMenu, newItem: DataRekomendasiMenu): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: DataRekomendasiMenu, newItem: DataRekomendasiMenu): Boolean {
                return oldItem == newItem
            }
        }
    }
}