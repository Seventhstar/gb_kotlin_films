package com.seventhstar.films.utils

import com.seventhstar.films.model.Favorite
import com.seventhstar.films.model.Film
import com.seventhstar.films.model.FilmDTO
import com.seventhstar.films.room.FavoritesEntity

fun convertFavoritesEntityToFavorite(all: List<FavoritesEntity>): List<Favorite> {
    return all.map { Favorite(it.id, it.filmId) }
}

fun convertFavoriteToFavoritesEntity(favorite: Film): FavoritesEntity {
    return FavoritesEntity(0, favorite.id, favorite.name, favorite.year, favorite.rating, favorite.description)
}

fun convertDtoToModel(films: List<FilmDTO>): List<Film> {
    return films.map {
        Film(it.id!!, it.title!!, it.getPosterURL())
    }
}