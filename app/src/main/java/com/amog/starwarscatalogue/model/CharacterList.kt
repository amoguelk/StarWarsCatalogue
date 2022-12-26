package com.amog.starwarscatalogue.model

import com.google.gson.annotations.SerializedName

data class CharacterList(
    @SerializedName("results")
    var results: ArrayList<Character>? = null
)
