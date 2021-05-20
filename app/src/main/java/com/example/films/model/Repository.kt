package com.example.films.model

interface Repository {
    fun getFilmsFromServer(): FilmsList
    fun getFilmsFromLocalStorage(): FilmsList
}
