package com.seventhstar.films.repository

import com.seventhstar.films.model.FilmsDTO
import retrofit2.Callback

class MainRepositoryImpl(private val remoteDataSource: RemoteDataSource) : MainRepository {
    override fun getFilmsFromServer(callback: Callback<FilmsDTO>) {
        remoteDataSource.getFilms(callback)
    }
}