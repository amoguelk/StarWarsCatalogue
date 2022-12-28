package com.amog.starwarscatalogue.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.amog.starwarscatalogue.databinding.MovieElementBinding
import com.amog.starwarscatalogue.model.Movie
import com.bumptech.glide.Glide

class MovieAdapter(private val context : Context, private val data : ArrayList<Movie>): BaseAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun getCount(): Int = data.size

    override fun getItem(i: Int): Movie = data[i]

    override fun getItemId(i: Int): Long = i.toLong()

    override fun getView(i: Int, p1: View?, p2: ViewGroup?): View {
        val binding = MovieElementBinding.inflate(inflater)
        with(binding) {
            tvTitle.text = data[i].title
            tvDirector.text = data[i].director
            tvReleaseDate.text = data[i].releaseDate
        }
        Glide.with(context)
            .load(data[i].cover)
            .into(binding.ivMovieCover)

        return binding.root
    }
}