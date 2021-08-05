package com.augie.moviecatalogue.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.augie.moviecatalogue.core.data.Resource
import com.augie.moviecatalogue.core.domain.model.Movie
import com.augie.moviecatalogue.core.domain.model.TvShow
import com.augie.moviecatalogue.core.domain.usecase.MovieUseCase

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun getTvShow(id: Int): LiveData<Resource<TvShow>> =
        movieUseCase.getDetailTvShow(id).asLiveData()

    fun getMovie(id: Int): LiveData<Resource<Movie>> = movieUseCase.getDetailMovies(id).asLiveData()

    fun setFavorite(id: Int, newState: Boolean, type: String) {
        when (type) {
            DetailActivity.MOVIE -> movieUseCase.setFavoriteMovie(id, newState)
            DetailActivity.TV_SHOW -> movieUseCase.setFavoriteTvShow(id, newState)
        }
    }
}