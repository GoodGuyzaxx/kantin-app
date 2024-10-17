package uningrat.kantin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uningrat.kantin.R
import uningrat.kantin.data.retrofit.response.DataTransaksi
import uningrat.kantin.databinding.ItemHistoryBinding

class HistoryAdapter: ListAdapter<DataTransaksi, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: DataTransaksi) {
            binding.tvOrderId.text = itemView.context.getString(R.string.Id_order, data.idOrder)
            binding.tvKantin.text = data.idKantin.toString()
            binding.tvDate.text = data.createdAt.toString()
            binding.tvPaymentType.text = data.tipePembayaran
            binding.tvStatusPesanan.text = data.statusPesanan
            binding.tvTotalHarga.text = itemView.context.getString(R.string.mata_uang, data.totalHarga.toString())
            binding.tvStatusPembayaran.text = data.statusPembayaran

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.HistoryViewHolder {
        val bindind = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HistoryViewHolder(bindind)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
        val list = getItem(position)
        holder.bind(list)
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataTransaksi>(){
            override fun areItemsTheSame(oldItem: DataTransaksi, newItem: DataTransaksi): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: DataTransaksi, newItem: DataTransaksi): Boolean {
                return oldItem == newItem
            }
        }
    }
}
