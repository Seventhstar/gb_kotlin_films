package com.seventhstar.films.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmFilter(
    val name: String = ""
) : Parcelable {

    @JvmName("getName1")
    fun getName(): String {
        return this.name
    }
}