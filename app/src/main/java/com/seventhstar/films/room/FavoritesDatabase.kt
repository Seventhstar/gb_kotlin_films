package com.seventhstar.films.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Delete
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FavoritesEntity::class), version = 3, exportSchema = false)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}

