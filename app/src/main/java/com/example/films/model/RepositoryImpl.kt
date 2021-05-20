package com.example.films.model

class RepositoryImpl : Repository {
    override fun getFilmsFromServer(): FilmsList {
        return FilmsList()
    }

    override fun getFilmsFromLocalStorage(): FilmsList {
        return FilmsList()
    }
}
