package com.augie.moviecatalogue.core.data.source.local

import com.augie.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.augie.moviecatalogue.core.data.source.local.entity.TvShowEntity
import com.augie.moviecatalogue.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource (private val movieDao: MovieDao) {

    fun getMovie(): Flow<List<MovieEntity>> = movieDao.getMovie()

    fun getTvShow(): Flow<List<TvShowEntity>> = movieDao.getTvShow()

    fun getMovieFavorite(): Flow<List<MovieEntity>> = movieDao.getMovieFavorite()

    fun getTvShowFavorite(): Flow<List<TvShowEntity>> = movieDao.getTvShowFavorite()

    fun getMovieById(id: Int): Flow<MovieEntity> = movieDao.getMovieById(id)

    fun getTvShowById(id: Int): Flow<TvShowEntity> = movieDao.getTvShowById(id)

    suspend fun insertMovie(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    suspend fun insertTvShow(tvShows: List<TvShowEntity>) = movieDao.insertTvShow(tvShows)

    fun setFavoriteMovie(id: Int, newState: Boolean) {
        movieDao.updateMovie(id, newState)
    }

    fun setFavoriteTvShow(id: Int, newState: Boolean) {
        movieDao.updateTvShow(id, newState)
    }
}