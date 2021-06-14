package com.seventhstar.films.repository

import com.seventhstar.films.model.Favorite
import com.seventhstar.films.model.Film
import com.seventhstar.films.room.FavoritesDao
import com.seventhstar.films.utils.convertFavoriteToFavoritesEntity
import com.seventhstar.films.utils.convertFavoritesEntityToFavorite


class LocalRepositoryImpl(private val localDataSource: FavoritesDao) :
    LocalRepository {
    override fun getAllFavorites(): List<Favorite> {
        return convertFavoritesEntityToFavorite(localDataSource.all())
    }

    override fun saveEntity(favorite: Film) {
        localDataSource.insert(convertFavoriteToFavoritesEntity(favorite))
    }
}