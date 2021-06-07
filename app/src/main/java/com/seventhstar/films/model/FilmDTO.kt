package com.seventhstar.films.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmDTO(
    val id: Int? = 0,
    val title: String? = "No title",
    val tagline: String? = "No tag line",
    val poster_path: String? = "No URL",
    val budget: Long? = 0,
    val overview: String? = "No desription",
    val vote_average: Double? = 0.0
) : Parcelable {
    fun getPosterURL(): String {
        return "https://image.tmdb.org/t/p/w500${this.poster_path}"
    }
}
