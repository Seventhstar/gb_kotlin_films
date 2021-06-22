package com.seventhstar.films.app

import com.seventhstar.films.model.Film
import com.seventhstar.films.model.FilmDTO
import com.seventhstar.films.model.FilmFilter

sealed class AppState {
    data class SetFilter(val filter: FilmFilter) : AppState()
    data class Success(val filmsData: List<Film>) : AppState()
    data class Loaded(val filmInfo: FilmDTO) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
