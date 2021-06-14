package com.seventhstar.films.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FavoritesEntity::class), version = 1, exportSchema = false)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}