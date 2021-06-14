package com.seventhstar.films.repository

import com.seventhstar.films.model.FilmDTO
import com.seventhstar.films.model.FilmsDTO

interface DetailsRepository {
    fun getDetailsFromServer(
        id: Int,
        callback: retrofit2.Callback<FilmDTO>
    )
}