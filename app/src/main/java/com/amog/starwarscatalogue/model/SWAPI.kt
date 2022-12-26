package com.amog.starwarscatalogue.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface SWAPI {
    @GET("api/people")
    fun getCharacters(): Call<CharacterList>
    @GET
    fun getPlanetDetail(@Url url: String?): Call<Planet>

}