package com.slyvii.eyeam.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.slyvii.eyeam.data.Animal
import com.slyvii.eyeam.data.ArticlesItem
import com.slyvii.eyeam.databinding.ListPopularAnimalBinding

class PopularAdapter(private val listAnimal: ArrayList<Animal>) :
    RecyclerView.Adapter<PopularAdapter.ListViewHolder>() {

    private lateinit var binding: ListPopularAnimalBinding
    private lateinit var onItemClickCallback: OnPopularClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnPopularClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        binding =
            ListPopularAnimalBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listAnimal[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClick(listAnimal[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listAnimal.size

    class ListViewHolder(itemView: ListPopularAnimalBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var tvName: TextView = itemView.tvNamePopular
        var imgPhoto: ImageView = itemView.ivPopularAnimal
        fun bind(data: Animal) {
            Glide.with(itemView.context)
                .load(data.image)
                .into(imgPhoto)
            tvName.text = data.name
        }
    }

    interface OnPopularClickCallback {
        fun onItemClick(data: Animal)
    }
}