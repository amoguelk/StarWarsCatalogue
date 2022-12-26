package com.amog.starwarscatalogue.view.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amog.starwarscatalogue.R
import com.amog.starwarscatalogue.databinding.ActivityPlanetDetailBinding
import com.amog.starwarscatalogue.model.Planet
import com.amog.starwarscatalogue.model.SWAPI
import com.amog.starwarscatalogue.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlanetDetail : AppCompatActivity() {
    private lateinit var binding: ActivityPlanetDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        overridePendingTransition(com.google.android.material.R.anim.abc_slide_in_bottom, com.google.android.material.R.anim.abc_slide_out_top)

        val bundle = intent.extras
        val planetURL = bundle?.getString("planetURL", "")

        CoroutineScope(Dispatchers.IO).launch {
            val call = Constants.getRetrofit().create(SWAPI::class.java).getPlanetDetail(planetURL)
            call.enqueue(object: Callback<Planet> {
                override fun onResponse(call: Call<Planet>, response: Response<Planet>) {
                    Log.d(Constants.LOGTAG, "Datos de servidor: ${response.body()}")
                    with(binding) {
                        pbLoad.visibility = View.GONE
                        tvPlanetName.text = response.body()?.name
                        tvPlanetClimate.text = getString(R.string.climate_text, response.body()?.climate)
                        tvPlanetDiameter.text = getString(R.string.diameter_text, response.body()?.diameter)
                        tvPlanetOrbit.text = getString(R.string.orbit_text, response.body()?.orbitalPeriod)
                        tvPlanetPopulation.text = getString(R.string.population_text, response.body()?.population)
                        tvPlanetRotation.text = getString(R.string.rotation_text, response.body()?.rotationPeriod)
                        tvPlanetTerrain.text = getString(R.string.terrain_text, response.body()?.terrain)
                    }
                }

                override fun onFailure(call: Call<Planet>, t: Throwable) {
                    binding.pbLoad.visibility = View.GONE
                    Log.d(Constants.LOGTAG, "ERROR: ${t.message}")
                    Toast.makeText(this@PlanetDetail, getString(R.string.error, t.message), Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}