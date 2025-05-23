package uningrat.kantin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uningrat.kantin.data.retrofit.response.DataRatingList
import uningrat.kantin.databinding.ItemDatailRatingBinding

class RatingDetailAdapter: ListAdapter<DataRatingList, RatingDetailAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder (private val binding: ItemDatailRatingBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data : DataRatingList) {
            binding.namaKonsumen.text = data.namaKonsumen
            val dataRating = data.rating.toFloat()
            binding.ratingKonsumen.rating = dataRating
            binding.tanggalRating.text = data.createdAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDatailRatingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        return holder.bind(item)

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataRatingList>(){
            override fun areItemsTheSame(
                oldItem: DataRatingList,
                newItem: DataRatingList
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataRatingList,
                newItem: DataRatingList
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}