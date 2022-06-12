package com.slyvii.eyeam.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.slyvii.eyeam.databinding.ItemDetailsBinding

class DetailAdapter(private val listAnimal: ArrayList<String>) :
    RecyclerView.Adapter<DetailAdapter.ListViewHolder>() {

    private lateinit var binding: ItemDetailsBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        binding =
            ItemDetailsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listAnimal[position])
    }

    override fun getItemCount(): Int = listAnimal.size

    class ListViewHolder(itemView: ItemDetailsBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val imgPhoto: ImageView = itemView.ivPopularAnimal
        fun bind(data: String) {
            Glide.with(itemView.context)
                .load(data)
                .into(imgPhoto)
        }
    }
}