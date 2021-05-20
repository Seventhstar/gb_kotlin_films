package com.example.films.viewmodel

import com.example.films.model.FilmsList

sealed class AppState {
    data class Success(val filmsData: FilmsList) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
