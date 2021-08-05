package com.augie.moviecatalogue.core.domain.model

data class TvShow(
    var id: Int,
    var title: String,
    var overview: String,
    var releaseDate: String,
    var genre: String,
    var duration: String,
    var poster: String,
    var backdrop: String,
    var isFav: Boolean = false
)
