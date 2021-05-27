package com.augie.moviecatalogue.ui.favorite.tvshow

import androidx.lifecycle.ViewModel
import com.augie.moviecatalogue.data.MovieRepository

class TvShowFavoriteViewModel(private val repository: MovieRepository) : ViewModel() {
    fun getTvShowFavorite() = repository.getFavoriteTvShows()
}