package com.levin.hw2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteActivity : AppCompatActivity() {
    companion object  {
        const val TOTAL_FAVORITES = "TOTAL_FAVORITES"
        const val prefID = "prefID_"
        const val pref_FILMNAME = "prefFN_"
        const val pref_FILMPOSTER = "prefFP_"
    }
    var items = mutableListOf(
        FavoriteItem(0, "Звездные войны", "film1", this)
    )
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerview) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var favID = 0
        var favFN = ""
        var favFP = ""

        var i = 0

        items.clear()
        val totalFavorites = intent.getIntExtra(TOTAL_FAVORITES,0 )

        if (totalFavorites == 0) {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        while (i < totalFavorites) {
            favID = intent.getIntExtra(prefID + i.toString(), 0)
            favFN = intent.getStringExtra(pref_FILMNAME + i.toString()).toString()
            favFP = intent.getStringExtra(pref_FILMPOSTER + i.toString()).toString()

            items.add(FavoriteItem(favID, favFN, favFP, this))
            i ++
        }
        setContentView(R.layout.activity_favorite)
        initRecycler()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.favorite_save -> {
                val intent = Intent()
                val totalFavorites = items.size
                var i = 0

                intent.putExtra(TOTAL_FAVORITES, totalFavorites)
                if (totalFavorites > 0) {
                    i = 0
                    while (i < totalFavorites) {
                        intent.putExtra(prefID + i.toString(), items[i].fID)
                        i++
                    }
                }
                setResult(RESULT_OK, intent);
                finish()
                true
            }
            R.id.favorite_cancel -> {
                val intent = Intent()
                setResult(RESULT_CANCELED, intent);
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = FavoriteAdapter(
            items,
            object : FavoriteAdapter.FavoriteClickListener {
                override fun onBtnRemoveClick(favoriteItem: FavoriteItem, position: Int) {
                    run {
                        items.removeAt(position)
                        recyclerView.adapter?.notifyDataSetChanged()
                        // change element -> notifyChange
                    }
                }
            })
    }
}