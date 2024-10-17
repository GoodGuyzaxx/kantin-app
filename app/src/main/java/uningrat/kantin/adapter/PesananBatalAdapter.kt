package uningrat.kantin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uningrat.kantin.R
import uningrat.kantin.data.retrofit.response.DataTransaksi
import uningrat.kantin.databinding.ItemPesananDibatalkanBinding

class PesananBatalAdapter: ListAdapter<DataTransaksi, PesananBatalAdapter.ViewHolder>(DIFF_CALLBACK) {


    inner class ViewHolder(private val binding: ItemPesananDibatalkanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataTransaksi) {
            binding.tvOrderId.text = itemView.context.getString(R.string.Id_order, data.idOrder)
            binding.tvOrderDate.text = data.createdAt
            binding.tvTotalPrice.text = itemView.context.getString(R.string.mata_uang, data.totalHarga)
            binding.tvPaymentStatus.text = itemView.context.getString(R.string.status_pembayaran, data.statusPembayaran)
            binding.tvPaymentMethod.text = itemView.context.getString(R.string.jenis_pembayaran, data.tipePembayaran )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PesananBatalAdapter.ViewHolder {
        val binding =
            ItemPesananDibatalkanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PesananBatalAdapter.ViewHolder, position: Int) {
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