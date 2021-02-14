package com.levin.hw2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FavoriteAdapter (val items : List<FavoriteItem>, private val clickListener: FavoriteClickListener): RecyclerView.Adapter<FavoriteVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_favorite,parent,false)
        return FavoriteVH(view)
    }

    override fun onBindViewHolder(holder: FavoriteVH, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.favoriteRmvBTN.setOnClickListener {
            clickListener.onBtnRemoveClick(item, position)
        }

    }

    override fun getItemCount() = items.size

    interface FavoriteClickListener {
        fun onBtnRemoveClick(favoriteItem: FavoriteItem, position: Int)
    }

}