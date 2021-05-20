package com.augie.moviecatalogue.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieEntity(
    // id will be auto-generated later(if using local database)
    var id: Int,
    var title: String,
    var overview: String,
    var releaseDate: String,
    var genre: String,
    var duration: String,
    var poster: String,
    var backdrop: String
) : Parcelable