package com.amog.starwarscatalogue.view.activities

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amog.starwarscatalogue.R
import com.amog.starwarscatalogue.databinding.ActivitySplashBinding
import com.amog.starwarscatalogue.model.*
import com.amog.starwarscatalogue.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Splash : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var mp : MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        overridePendingTransition(com.google.android.material.R.anim.abc_slide_out_top, com.google.android.material.R.anim.abc_slide_out_top)

        mp = MediaPlayer.create(this, R.raw.swtheme)
        mp.start()

        CoroutineScope(Dispatchers.IO).launch {
            var characters : ArrayList<Character>? = null
            var movies : ArrayList<Movie>?
            var loadSuccess = false
            val callCharacters = Constants.getRetrofit().create(SWAPI::class.java).getCharacters()
            callCharacters.enqueue(object: Callback<CharacterList> {
                override fun onResponse(
                    call: Call<CharacterList>,
                    response: Response<CharacterList>
                ) {
                    Log.d(Constants.LOGTAG, "Datos de personajes: ${response.body()?.results}")
                    characters = response.body()!!.results
                    loadSuccess = true
                }

                override fun onFailure(call: Call<CharacterList>, t: Throwable) {
                    binding.pbLoad.visibility = View.GONE
                    Log.d(Constants.LOGTAG, "ERROR: ${t.message}")
                    Toast.makeText(this@Splash, getString(R.string.error, t.message), Toast.LENGTH_LONG).show()
                }
            })
            // Al ser sólo 6 películas en el API, conviene jalar todas y luego seleccionar las que nos
            //  interesan, en vez de jalar una por una
            val callMovies = Constants.getRetrofit().create(SWAPI::class.java).getMovies()
            callMovies.enqueue(object: Callback<MovieList> {
                override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                    Log.d(Constants.LOGTAG, "Datos de películas: ${response.body()?.results}")
                    movies = response.body()!!.results
                    for (i in 0 until movies!!.size)
                        movies!![i].cover = resources.getStringArray(R.array.movieURLs)[i]
                    if (loadSuccess) {
                        val intent = Intent(this@Splash, MainActivity::class.java)
                        val bundle = Bundle()
                        bundle.putParcelableArrayList("characters", characters)
                        bundle.putParcelableArrayList("movies", movies)
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<MovieList>, t: Throwable) {
                    binding.pbLoad.visibility = View.GONE
                    Log.d(Constants.LOGTAG, "ERROR: ${t.message}")
                    Toast.makeText(this@Splash, getString(R.string.error, t.message), Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    override fun onPause() {
        super.onPause()
        mp.pause()
    }

    override fun onRestart() {
        super.onRestart()
        mp.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mp.stop()
    }
}