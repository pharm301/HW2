package com.levin.hw2


import android.app.Application
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class FilmVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val filmnameTV : TextView = itemView.findViewById(R.id.filmname)
    val filmdetailTV : TextView = itemView.findViewById(R.id.filmdetail)
    val filmposterIV : ImageView = itemView.findViewById(R.id.filmPoster)
    val filmfavorIV : ImageView = itemView.findViewById(R.id.favor)

    fun bind (item : FilmItem) {
//        val resID = item.context.resources.getIdentifier(item.postername,"drawable",item.context.packageName)
        val resID = itemView.context.resources.getIdentifier(item.postername,"drawable",itemView.context.packageName)
        filmnameTV.text = item.filmname
        filmdetailTV.text = item.filmdetail
        filmposterIV.background = itemView.context.resources.getDrawable(resID,null)
//        filmposterIV.background = item.context.resources.getDrawable(resID,null)
        if (item.favor)  filmfavorIV.background = itemView.context.resources.getDrawable(R.drawable.is_favor)
        else filmfavorIV.background = itemView.context.resources.getDrawable(R.drawable.no_favor)

    }
}