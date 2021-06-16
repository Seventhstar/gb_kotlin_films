package com.seventhstar.films.model

data class Favorite(
    val id: Long,
    val filmId: Int,
    val name: String,
    val posterUrl: String,
    val year: Int,
    val rating: Double,
    val description: String
)