package com.augie.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.augie.moviecatalogue.data.MovieEntity
import com.augie.moviecatalogue.data.MovieRepository

class DetailMovieViewModel(private val repository: MovieRepository) : ViewModel() {
    fun getMovie(type: String, id: Int): LiveData<MovieEntity> {
        return when (type) {
            DetailMovieActivity.MOVIE -> repository.getDetailMovies(id)
            else -> repository.getDetailTvShow(id)
        }
    }
}