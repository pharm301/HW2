package com.levin.hw2

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavoriteVH( itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val filmnameTV : TextView = itemView.findViewById(R.id.filmname)
    private val filmposterIV : ImageView = itemView.findViewById(R.id.filmPoster)
    val favoriteRmvBTN: Button = itemView.findViewById(R.id.btnRemove)

    fun bind (item : FavoriteItem) {
        val resID = item.context.resources.getIdentifier(item.postername,"drawable",item.context.packageName)
        filmnameTV.text = item.filmname
        filmposterIV.background = item.context.resources.getDrawable(resID,null)
    }
}