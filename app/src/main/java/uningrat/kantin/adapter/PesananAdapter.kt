package uningrat.kantin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uningrat.kantin.data.retrofit.response.DataTransaksi
import uningrat.kantin.databinding.ItemPemesananBinding

class PesananAdapter(private val listener: OnPesananStatusClickListener): ListAdapter<DataTransaksi, PesananAdapter.ViewHolder>(DIFF_CALLBACK) {

    interface OnPesananStatusClickListener {
        fun onPesananStatusClick(data: DataTransaksi)

        fun onUpdateStatus(data: DataTransaksi)
    }


    inner class ViewHolder(private val binding: ItemPemesananBinding): RecyclerView.ViewHolder(binding.root){
        private val rvDetailMenu: RecyclerView = binding.rvMenuItem
        fun bind(data: DataTransaksi) {
            binding.tvNama.text = data.namaKonsumen

            rvDetailMenu.layoutManager = LinearLayoutManager(itemView.context)
            rvDetailMenu.adapter = PesananDetailAdapter().apply {
                submitList(data.menu)
            }
            binding.btnPesananSelesai.setOnClickListener {
                listener.onPesananStatusClick(data)
            }

            binding.btnBatalkan.setOnClickListener {
                listener.onUpdateStatus(data)
            }

            val valuePesanan = data.statusPesanan
            when (valuePesanan) {
                "Diproses" -> binding.btnPesananSelesai.text = "Pesanan Selesai"
                "Diterima" -> binding.btnPesananSelesai.text = "Pesanan Diproses"
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PesananAdapter.ViewHolder {
        val binding = ItemPemesananBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder:PesananAdapter.ViewHolder, position: Int) {
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
