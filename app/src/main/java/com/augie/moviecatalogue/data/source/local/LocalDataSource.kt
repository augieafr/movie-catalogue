package com.augie.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.augie.moviecatalogue.data.source.local.entity.MovieEntity
import com.augie.moviecatalogue.data.source.local.entity.TvShowEntity
import com.augie.moviecatalogue.data.source.local.room.MovieDao

class LocalDataSource private constructor(private val movieDao: MovieDao){

    fun getMovie(): DataSource.Factory<Int, MovieEntity> = movieDao.getMovie()

    fun getTvShow(): DataSource.Factory<Int, TvShowEntity> = movieDao.getTvShow()

    fun getMovieFavorite(): DataSource.Factory<Int, MovieEntity> = movieDao.getMovieFavorite()

    fun getTvShowFavorite(): DataSource.Factory<Int, TvShowEntity> = movieDao.getTvShowFavorite()

    fun getMovieById(id: Int): LiveData<MovieEntity> = movieDao.getMovieById(id)

    fun getTvShowById(id: Int): LiveData<TvShowEntity> = movieDao.getTvShowById(id)

    fun insertMovie(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    fun insertTvShow(tvShows: List<TvShowEntity>) = movieDao.insertTvShow(tvShows)

    fun setFavoriteMovie(id: Int, newState: Boolean) {
        movieDao.updateMovie(id, newState)
    }

    fun setFavoriteTvShow(id: Int, newState: Boolean) {
        movieDao.updateTvShow(id, newState)
    }

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(academyDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(academyDao)
    }
}