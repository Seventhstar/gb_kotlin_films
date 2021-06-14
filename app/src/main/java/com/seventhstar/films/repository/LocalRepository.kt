package com.seventhstar.films.repository

import com.seventhstar.films.model.Favorite
import com.seventhstar.films.model.Film

interface LocalRepository {
    fun getAllFavorites(): List<Favorite>
    fun saveEntity(favorite: Film)
}