package com.seventhstar.films.app

import android.app.Application
import androidx.room.Room
import com.seventhstar.films.room.FavoritesDao
import com.seventhstar.films.room.FavoritesDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        private var appInstance: App? = null
        private var db: FavoritesDatabase? = null
        private const val DB_NAME = "favorites.db"

        fun getFavoritesDao(): FavoritesDao {
            if (db == null) {
                synchronized(FavoritesDatabase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            FavoritesDatabase::class.java,
                            DB_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }

            return db!!.favoritesDao()
        }
    }


}