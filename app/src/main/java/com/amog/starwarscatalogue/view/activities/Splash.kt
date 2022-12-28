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
import com.amog.starwarscatalogue.model.CharacterList
import com.amog.starwarscatalogue.model.SWAPI
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
            val call = Constants.getRetrofit().create(SWAPI::class.java).getCharacters()
            call.enqueue(object: Callback<CharacterList> {
                override fun onResponse(
                    call: Call<CharacterList>,
                    response: Response<CharacterList>
                ) {
                    Log.d(Constants.LOGTAG, "Datos de servidor: ${response.body()?.results}")
                    val intent = Intent(this@Splash, MainActivity::class.java)
                    val bundle = Bundle()
                    bundle.putParcelableArrayList("characters", response.body()!!.results)
                    intent.putExtras(bundle)
                    startActivity(intent)
                    finish()
                }

                override fun onFailure(call: Call<CharacterList>, t: Throwable) {
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