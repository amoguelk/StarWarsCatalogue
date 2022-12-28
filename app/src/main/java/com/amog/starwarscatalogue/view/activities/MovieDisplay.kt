package com.amog.starwarscatalogue.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amog.starwarscatalogue.R
import com.amog.starwarscatalogue.databinding.ActivityMovieDisplayBinding
import com.bumptech.glide.Glide

class MovieDisplay : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDisplayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        overridePendingTransition(com.google.android.material.R.anim.abc_slide_in_bottom, com.google.android.material.R.anim.abc_slide_out_top)

        val bundle = intent.extras
        val movies = bundle?.getStringArrayList("movies")
        val ivArr = arrayOf(binding.ivMovie1, binding.ivMovie2, binding.ivMovie3, binding.ivMovie4, binding.ivMovie5, binding.ivMovie6)
        for (i in 0 until movies!!.size) {
            val m = movies[i]
            val numIndex = m.substring(0, m.length - 1).lastIndexOf('/') + 1
            val movieIndex = m[numIndex].digitToInt()
            Glide.with(this)
                .load(resources.getStringArray(R.array.movieURLs)[movieIndex - 1])
                .into(ivArr[i])
        }
    }
}