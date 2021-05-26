package com.augie.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.augie.moviecatalogue.data.source.local.entity.MovieEntity
import com.augie.moviecatalogue.data.MovieRepository
import com.augie.moviecatalogue.vo.Resource

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
    fun getMovie(): LiveData<Resource<PagedList<MovieEntity>>> = repository.getMovies()
}