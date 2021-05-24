package com.seventhstar.films.model

class RepositoryImpl : Repository {
    override fun getFilmsFromServer(): FilmsList {
        return FilmsList()
    }

    override fun getFilmsFromLocalStorage(): List<Film> {
        return getLocalFilms()
    }


}
