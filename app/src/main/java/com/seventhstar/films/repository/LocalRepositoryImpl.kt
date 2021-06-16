package com.seventhstar.films.repository

import com.seventhstar.films.model.Favorite
import com.seventhstar.films.model.Film
import com.seventhstar.films.room.FavoritesDao
import com.seventhstar.films.utils.convertFavoriteToFavoritesEntity
import com.seventhstar.films.utils.convertFavoritesEntityToFavorite
import com.seventhstar.films.utils.convertFavoritesEntityToModel


class LocalRepositoryImpl(private val localDataSource: FavoritesDao) :
    LocalRepository {

    override fun isFavorite(id: Int): Boolean {
        return localDataSource.getFavoriteByID(id).isNotEmpty()
    }

    override fun getFavoriteByID(id: Int): Film {
        val a = localDataSource.getFavoriteByID(id)
        if (a.isNotEmpty()) return convertFavoritesEntityToModel(a[0])
        return Film()
    }

    override fun getAllFavorites(): List<Favorite> {
        return convertFavoritesEntityToFavorite(localDataSource.all())
    }

    override fun saveEntity(favorite: Film) {
        localDataSource.insert(convertFavoriteToFavoritesEntity(favorite))
    }
}