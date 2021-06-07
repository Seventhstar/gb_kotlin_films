package com.seventhstar.films.model

import android.os.Build
import androidx.annotation.RequiresApi

class RepositoryImpl : Repository {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun getFilmsFromServer(): List<Film> {
        return getServerFilms()
    }

    override fun getFilmsFromLocalStorage(): List<Film> {
        return getLocalFilms()
    }


}
