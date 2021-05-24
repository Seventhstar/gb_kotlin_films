package com.seventhstar.films.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val name: String,
    val imgUrl: String,
    val year: Int,
    val rating: Double,
    val description: String
) : Parcelable

fun getLocalFilms(): List<Film> {
    return listOf(
        Film(
            "Lethal Weapon",
            "https://upload.wikimedia.org/wikipedia/ru/thumb/f/f1/Lethal_Weapon_Poster.jpg/201px-Lethal_Weapon_Poster.jpg",
            1987,
            7.6, "good film"
        ),
        Film(
            "Die Hard",
            "https://upload.wikimedia.org/wikipedia/ru/thumb/f/f6/Die_Hard.gif/211px-Die_Hard.gif",
            1988,
            8.2, "good film"
        ),
        Film(
            "Sicario",
            "https://lh3.googleusercontent.com/proxy/JNdjrkl4fDOWy7KmzCjSlWE_xb5tsBvPqtK0gbROwABN1ivqjCH24w5J3bJL1U7xKq6a2oFIUKzAqhcJTwzczlUGWc6I3yvPc4XqITobSFSk1YP2jL6UxKefSV_ihgfrcUIKq17MMbmVq9zOcNi-Gq-TQorrzCtNfhigXvXM4EI",
            2015,
            7.6, "good film"
        )
    )
}
