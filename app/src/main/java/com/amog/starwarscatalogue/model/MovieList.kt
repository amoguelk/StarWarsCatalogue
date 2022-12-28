package com.amog.starwarscatalogue.model

import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("results")
    var results: ArrayList<Movie>? = null
)
