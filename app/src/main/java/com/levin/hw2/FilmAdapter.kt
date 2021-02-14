package com.levin.hw2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FilmAdapter (val items : List<FilmItem>, private val clickListener: FilmClickListener): RecyclerView.Adapter<FilmVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_film,parent,false)
        return FilmVH(view)
    }

    override fun onBindViewHolder(holder: FilmVH, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.filmfavorIV.setOnClickListener {
            clickListener.onFavoriteClick(item, position)
        }

        holder.itemView.setOnClickListener {
            clickListener.onItemClick(item, position)
        }
    }

    override fun getItemCount() = items.size

    interface FilmClickListener {
        fun onFavoriteClick(filmItem : FilmItem, position: Int)
        fun onItemClick (filmItem: FilmItem, position: Int)
    }

}