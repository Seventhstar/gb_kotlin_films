package com.seventhstar.films.repository;

import com.seventhstar.films.model.FilmsDTO;

interface MainRepository {
    fun getFilmsFromServer(
        callback: retrofit2.Callback<FilmsDTO>
    )
}

