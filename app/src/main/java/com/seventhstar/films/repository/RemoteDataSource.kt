package com.seventhstar.films.repository

import com.google.gson.GsonBuilder
import com.seventhstar.films.BuildConfig
import com.seventhstar.films.model.FilmDTO
import com.seventhstar.films.model.FilmsDTO
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    private val apiKey: String = BuildConfig.API_KEY

    private val filmsApi = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(FilmsApi::class.java)

    fun getFilms(callback: Callback<FilmsDTO>) {
        filmsApi.getFilms(apiKey).enqueue(callback)
    }

    fun getFilmInfo(id: Int, callback: Callback<FilmDTO>) {
        filmsApi.getFilmInfo(id, apiKey).enqueue(callback)
    }
}
