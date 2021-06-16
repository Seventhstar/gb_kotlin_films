package com.seventhstar.films.repository

import com.seventhstar.films.model.Favorite
import com.seventhstar.films.model.Film

interface LocalRepository {
    fun isFavorite(id: Int): Boolean
    fun getFavoriteByID(id: Int): Film
    fun getAllFavorites(): List<Favorite>
    fun saveEntity(favorite: Film)
}