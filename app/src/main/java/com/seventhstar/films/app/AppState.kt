package com.seventhstar.films.app

import com.seventhstar.films.model.Film
import com.seventhstar.films.model.FilmDTO

sealed class AppState {
    data class Success(val filmsData: List<Film>) : AppState()
    data class Loaded(val filmInfo: FilmDTO) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
