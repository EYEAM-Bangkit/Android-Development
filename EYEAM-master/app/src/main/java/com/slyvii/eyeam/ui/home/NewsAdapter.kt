package com.slyvii.eyeam.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.slyvii.eyeam.data.ArticlesItem
import com.slyvii.eyeam.databinding.ItemNewsBinding

class NewsAdapter(private val listNews: ArrayList<ArticlesItem>) :
    RecyclerView.Adapter<NewsAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnNewsClickCallback
    private lateinit var binding: ItemNewsBinding

    fun setOnItemClickCallback(onItemClickCallback: OnNewsClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listNews[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClick(listNews[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listNews.size

    class ListViewHolder(itemView: ItemNewsBinding) : RecyclerView.ViewHolder(itemView.root) {
        var tvName: TextView = itemView.tvTitleNews
        var tvDate: TextView = itemView.tvDate
        private var imgPhoto: ImageView = itemView.imgNews
        fun bind(data: ArticlesItem) {
            Glide.with(itemView.context)
                .load(data.urlToImage)
                .into(imgPhoto)
            tvName.text = data.title
            tvDate.text = "${data.publishedAt?.subSequence(0,10)} ${data.publishedAt?.subSequence(11,16)}"
        }
    }

    interface OnNewsClickCallback {
        fun onItemClick(data: ArticlesItem)
    }
}