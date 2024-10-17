package uningrat.kantin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uningrat.kantin.R
import uningrat.kantin.data.retrofit.response.DataTransaksi
import uningrat.kantin.databinding.ItemTransaksiBinding

class RiwayatAdapter: ListAdapter<DataTransaksi, RiwayatAdapter.ViewHolder>(DIFF_CALLBACK) {


    inner class ViewHolder(private val binding: ItemTransaksiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataTransaksi) {
            binding.tvOrderId.text =data.idOrder
            binding.tvOrderId.text = data.createdAt
            binding.tvTotalPrice.text = itemView.context.getString(R.string.mata_uang, data.totalHarga.toString())
            binding.tvPaymentType.text = itemView.context.getString(R.string.jenis_pembayaran, data.tipePembayaran)
            binding.tvOrderStatus.text = itemView.context.getString(R.string.status_pembayaran, data.statusPesanan)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RiwayatAdapter.ViewHolder {
        val binding =
            ItemTransaksiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RiwayatAdapter.ViewHolder, position: Int) {
        val list = getItem(position)
        holder.bind(list)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataTransaksi>() {
            override fun areItemsTheSame(oldItem: DataTransaksi, newItem: DataTransaksi): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataTransaksi,
                newItem: DataTransaksi
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}