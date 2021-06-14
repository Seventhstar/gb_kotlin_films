package com.seventhstar.films.repository

import com.seventhstar.films.model.FilmDTO
import com.seventhstar.films.model.FilmsDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface FilmsApi {
    @GET("3/movie/{id}")
    fun getFilmInfo(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Call<FilmDTO>

    @GET("3/movie/popular")
    fun getFilms(
        @Query("api_key") apiKey: String
    ): Call<FilmsDTO>
}