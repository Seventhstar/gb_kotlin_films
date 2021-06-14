package com.seventhstar.films.repository

import com.seventhstar.films.model.FilmDTO
import com.seventhstar.films.model.FilmsDTO
import retrofit2.Callback

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) :
    DetailsRepository {
    override fun getDetailsFromServer(id: Int, callback: Callback<FilmDTO>) {
        remoteDataSource.getFilmInfo(id, callback)
    }
}