package com.augie.moviecatalogue.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.augie.moviecatalogue.data.MovieRepository
import com.augie.moviecatalogue.data.source.local.entity.MovieEntity

class MovieFavoriteViewModel(private var repository: MovieRepository) : ViewModel() {
    fun getMovieFavorite(): LiveData<PagedList<MovieEntity>> = repository.getFavoriteMovies()
}