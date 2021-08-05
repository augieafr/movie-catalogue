package com.augie.moviecatalogue.home.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.augie.moviecatalogue.core.domain.usecase.MovieUseCase

class MovieViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val movie = movieUseCase.getMovies().asLiveData()
}