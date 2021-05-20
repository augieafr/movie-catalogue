package com.augie.moviecatalogue.data

import androidx.lifecycle.LiveData

interface MovieDataSource {
    fun getMovies(): LiveData<List<MovieEntity>>
    fun getTvShows(): LiveData<List<MovieEntity>>
    fun getDetailMovies(id: Int): LiveData<MovieEntity>
    fun getDetailTvShow(id: Int): LiveData<MovieEntity>
}