package com.seventhstar.films.room

import androidx.room.*

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM FavoritesEntity")
    fun all(): List<FavoritesEntity>

    @Query("SELECT * FROM FavoritesEntity WHERE filmId LIKE :id")
    fun getFavoriteByID(id: Long): List<FavoritesEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: FavoritesEntity)

    @Update
    fun update(entity: FavoritesEntity)

    @Delete
    fun delete(entity: FavoritesEntity)
}