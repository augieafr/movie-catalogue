package com.augieafr.moviecatalogue.favorite.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.augie.moviecatalogue.core.domain.usecase.MovieUseCase

class TvShowFavoriteViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun getTvShowFavorite() = movieUseCase.getFavoriteTvShows().asLiveData()
}