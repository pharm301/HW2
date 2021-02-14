package com.levin.hw2


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.*
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    companion object{
        const val myRequestFA = 1
        const val TOTAL_FAVORITES = "TOTAL_FAVORITES"
        const val prefID = "prefID_"
        const val pref_FILMNAME = "prefFN_"
        const val pref_FILMPOSTER = "prefFP_"
    }
    var items = mutableListOf(
        FilmItem (0, "Звездные войны","Давным давно, в очень далекой галактике...","film4", false, this),
        FilmItem (0,"Мандалорец","После победы Республики, в очень далекой галактике...","film1", false,this ),
        FilmItem (0,"Терминатор 2","Скайнет посылает нового Терминатора Т1000","film3", false, this ),
        FilmItem (0,"Игра престолов","Захватывающее противостояние от известного ватаката в жанре фентези","film2", false,this),
        FilmItem (0,"Чужие","История про ужасных инопланетных чудовищ продолжается. Рипли опять в деле","film5", false,this),
        FilmItem (0,"Робокоп","Бандиты недалекого будущего станут столь суровы, что для борьбы с ними создадут Киборга","film6", false,this),
        FilmItem (0,"Выход Дракона","Брюс Ли всех подбъет, пусть даже врагов будет целый остров","film7", false,this),
        FilmItem (0,"Хороший, плохой, злой","Выдающийся спагетти-вестерн. Все звезды жанра - Трус, Бывалый и Балбес на мексиканский лад","film8", false,this),
        FilmItem (0,"Проект А","Почти Операция Ы, но в главной роли Джеки Чан и его друзья","film9", false,this),
        FilmItem (0,"Гладиатор","Рассел Кроу в роли Настоящего Мужика времен Римской Империи","film10", false,this),
    )

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerview) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var totalFavorites = 0
        var i = 0

        while (i < items.size){
            items[i].fID = i
            items[i].favor = false
            i++
        }

//        items.forEachIndexed { index, filmItem -> filmItem.fID = index }
//        items.forEach { filmItem -> filmItem.favor = false }

        if (savedInstanceState?.getInt(TOTAL_FAVORITES, totalFavorites) != null) {
            savedInstanceState.getInt(TOTAL_FAVORITES, totalFavorites).also { totalFavorites = it }
            if (totalFavorites > 0) {
                i = 0
                while (i < totalFavorites){
                    items[savedInstanceState.getInt(prefID+i.toString(),0)].favor = true
                    i++
                }
            }
        }
        setContentView(R.layout.activity_main)

        initRecycler()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_exit -> {
                this.onBackPressed()
                true
            }
            R.id.action_favorite -> {
                var favID = 0
                var favFN = ""
                var favFP = ""

                val totalFavorites= items.count { filmItem -> filmItem.favor  }
                var i = 0
                var k = 0
                if (totalFavorites > 0){
                    val intent = Intent(this,FavoriteActivity::class.java)
                    intent.putExtra(TOTAL_FAVORITES, totalFavorites)
                    while (i < items.size){
                        if (items[i].favor){
                            favID = items[i].fID
                            favFN = items[i].filmname
                            favFP = items[i].postername

                            intent.apply {
                                putExtra(prefID + k.toString(), favID)
                                putExtra(pref_FILMNAME + k.toString(), favFN)
                                putExtra(pref_FILMPOSTER + k.toString(), favFP)
                            }
                            k++
                        }
                        i++
                    }
                    startActivityForResult(intent,myRequestFA)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    private fun initRecycler() {

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        else {
            recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        }
//        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = FilmAdapter(items, object : FilmAdapter.FilmClickListener {

            override fun onFavoriteClick(filmItem: FilmItem, position: Int) {
                // change element -> notifyChange
                filmItem.favor = !filmItem.favor
                recyclerView.adapter?.notifyItemChanged(position)
                if (filmItem.favor)
                    Toast.makeText(this@MainActivity, "Добавлено",Toast.LENGTH_SHORT).show()
                else Toast.makeText(this@MainActivity, "Удалено",Toast.LENGTH_SHORT).show()
            }

            override fun onItemClick(filmItem: FilmItem, position: Int) {
                // change element -> notifyDelete

            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == myRequestFA && resultCode == Activity.RESULT_OK) {
            val totalFavorites = data!!.getIntExtra(TOTAL_FAVORITES,0)

            items.forEach { filmItem -> filmItem.favor = false }
            if (totalFavorites > 0) {
                var i : Int = 0
                while (i < totalFavorites){
                    items[data.getIntExtra(prefID+i.toString(),0)].favor = true
                    i++
                }
            }
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val totalFavorites: Int = items.count { filmItem -> filmItem.favor  }
        outState.putInt(TOTAL_FAVORITES, totalFavorites)

        if (totalFavorites > 0) {
            var i = 0
            var k = 0
            while (i < items.size) {
                if (items[i].favor) {
                    outState.putInt(prefID + k.toString(), items[i].fID)
                    k++
                }
                i++
            }
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.ExitAlertTitle)
            setMessage(R.string.ExitAlertMsg)

            setPositiveButton(R.string.ExitAlertYes) { _, _ ->
                super.onBackPressed()
            }

            setNegativeButton(R.string.ExitAlertNo){_, _ ->
                // if user press no, then return the activity
            }
            setCancelable(true)
        }.create().show()
    }
}