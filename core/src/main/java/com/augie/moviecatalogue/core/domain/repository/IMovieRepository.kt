package com.augie.moviecatalogue.core.domain.repository

import com.augie.moviecatalogue.core.data.Resource
import com.augie.moviecatalogue.core.domain.model.Movie
import com.augie.moviecatalogue.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(): Flow<Resource<List<Movie>>>
    fun getTvShows(): Flow<Resource<List<TvShow>>>

    fun getDetailMovies(id: Int): Flow<Resource<Movie>>
    fun getDetailTvShow(id: Int): Flow<Resource<TvShow>>

    fun getFavoriteMovies(): Flow<List<Movie>>
    fun getFavoriteTvShows(): Flow<List<TvShow>>

    fun setFavoriteMovie(id: Int, newState: Boolean)
    fun setFavoriteTvShow(id: Int, newState: Boolean)

}