package com.augieafr.moviecatalogue.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.augie.moviecatalogue.core.domain.model.Movie
import com.augie.moviecatalogue.core.domain.usecase.MovieUseCase

class MovieFavoriteViewModel(private var movieUseCase: MovieUseCase) : ViewModel() {
    fun getMovieFavorite(): LiveData<List<Movie>> = movieUseCase.getFavoriteMovies().asLiveData()
}