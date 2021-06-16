package com.seventhstar.films.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val filmId: Int,
    val name: String,
    val posterUrl: String,
    val year: Int,
    val rating: Double,
    val description: String
)