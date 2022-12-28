package com.amog.starwarscatalogue.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.amog.starwarscatalogue.databinding.ActivityMovieDisplayBinding
import com.amog.starwarscatalogue.model.Movie
import com.amog.starwarscatalogue.view.adapter.MovieAdapter

class MovieDisplay : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDisplayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        overridePendingTransition(com.google.android.material.R.anim.abc_slide_in_bottom, com.google.android.material.R.anim.abc_slide_out_top)

        val bundle = intent.extras
        val movieURLs = bundle?.getStringArrayList("movieURLs")
        val allMovies = bundle?.getParcelableArrayList<Movie>("movies")
        val movies = ArrayList<Movie>()

        for (i in 0 until movieURLs!!.size) {
            val m = movieURLs[i]
            // Extraemos del URL del API el número de película
            val numIndex = m.substring(0, m.length - 1).lastIndexOf('/') + 1
            val movieIndex = m[numIndex].digitToInt() - 1
            // Usamos el número de película para obtener el URL de la imágen
            movies.add(allMovies?.get(movieIndex)!!)
        }

        with (binding) {
            pbLoad.visibility = View.GONE
            lvMovies.adapter = MovieAdapter(this@MovieDisplay, movies)
            lvMovies.divider = null
        }
    }
}