package com.amog.starwarscatalogue.view.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.amog.starwarscatalogue.R
import com.amog.starwarscatalogue.databinding.ActivityMainBinding
import com.amog.starwarscatalogue.model.Character
import com.amog.starwarscatalogue.model.Movie
import com.amog.starwarscatalogue.view.adapter.CharacterAdapter


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var movies: ArrayList<Movie>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        overridePendingTransition(com.google.android.material.R.anim.abc_slide_in_bottom, com.google.android.material.R.anim.abc_slide_out_top)

        val bundle = intent.extras
        val characters = bundle?.getParcelableArrayList<Character>("characters")
        movies = bundle?.getParcelableArrayList<Movie>("movies")
        if (characters != null) {
            binding.rvCatalogue.layoutManager = LinearLayoutManager(this)
            binding.rvCatalogue.adapter = CharacterAdapter(this, characters)
        }
    }

    fun menuClick(character: Character) {
        val items = resources.getStringArray(R.array.options)
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.info_dialog_title)).setIcon(R.drawable.ic_more)
            .setItems(items) {dialog, selected ->
                if (selected == 0) { // Planet
                    val intent = Intent(this, PlanetDetail::class.java)
                    val bundle = Bundle()
                    bundle.putString("planetURL", character.homeworld)
                    intent.putExtras(bundle)
                    startActivity(intent)
                } else if (selected == 1) {
                    val intent = Intent(this, MovieDisplay::class.java)
                    val bundle = Bundle()
                    bundle.putStringArrayList("movieURLs", character.movies)
                    bundle.putParcelableArrayList("movies", movies)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }
            .setIcon(R.drawable.ic_more)
            .create()
            .show()
    }
}