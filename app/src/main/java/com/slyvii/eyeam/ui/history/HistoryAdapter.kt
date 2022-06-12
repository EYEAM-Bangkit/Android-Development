package com.slyvii.eyeam.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.slyvii.eyeam.data.ArticlesItem
import com.slyvii.eyeam.data.DataItems
import com.slyvii.eyeam.databinding.ItemLogsBinding
import com.slyvii.eyeam.ui.home.NewsAdapter

class HistoryAdapter(private val listHistory: ArrayList<DataItems>) :
    RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {

    private lateinit var binding: ItemLogsBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        binding =
            ItemLogsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }

    override fun getItemCount(): Int = listHistory.size

    class ListViewHolder(itemView: ItemLogsBinding) : RecyclerView.ViewHolder(itemView.root) {
        var tvName: TextView = itemView.tvTitleNews
        var tvDate: TextView = itemView.tvDate
        fun bind(data: DataItems) {
            tvName.text = data.animal
            tvDate.text = data.logtime
        }
    }

}