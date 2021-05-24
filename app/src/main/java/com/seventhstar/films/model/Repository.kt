package com.seventhstar.films.model

interface Repository {
    fun getFilmsFromServer(): FilmsList
    fun getFilmsFromLocalStorage(): List<Film>
}
