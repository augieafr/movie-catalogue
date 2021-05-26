package com.augie.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.augie.moviecatalogue.data.MovieRepository
import com.augie.moviecatalogue.data.source.local.entity.MovieEntity
import com.augie.moviecatalogue.data.source.local.entity.TvShowEntity
import com.augie.moviecatalogue.vo.Resource

class DetailMovieViewModel(private val repository: MovieRepository) : ViewModel() {

    fun getTvShow(id: Int): LiveData<Resource<TvShowEntity>> = repository.getDetailTvShow(id)
    fun getMovie(id: Int): LiveData<Resource<MovieEntity>> = repository.getDetailMovies(id)

    fun setFavorite(id: Int, newState: Boolean, type: String) {
        when(type) {
            DetailMovieActivity.MOVIE -> repository.setFavoriteMovie(id, newState)
            DetailMovieActivity.TV_SHOW -> repository.setFavoriteTvShow(id, newState)
        }
    }
}