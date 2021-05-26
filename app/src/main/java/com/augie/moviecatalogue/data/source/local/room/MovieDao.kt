package com.augie.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.augie.moviecatalogue.data.source.local.entity.MovieEntity
import com.augie.moviecatalogue.data.source.local.entity.TvShowEntity

@Dao
interface MovieDao {

    @Query("select * from movie_entity")
    fun getMovie(): DataSource.Factory<Int, MovieEntity>

    @Query("select * from tvShow_entity")
    fun getTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Query("select * from movie_entity where favorite = 1")
    fun getMovieFavorite(): DataSource.Factory<Int, MovieEntity>

    @Query("select * from tvShow_entity where favorite = 1")
    fun getTvShowFavorite(): DataSource.Factory<Int, TvShowEntity>

    @Query("select * from movie_entity where id = :id")
    fun getMovieById(id: Int): LiveData<MovieEntity>

    @Query("select * from tvShow_entity where id = :id")
    fun getTvShowById(id: Int): LiveData<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShows: List<TvShowEntity>)

    @Query("update movie_entity set favorite = :newState where id = :id")
    fun updateMovie(id: Int, newState: Boolean)

    @Query("update tvShow_entity set favorite = :newState where id = :id")
    fun updateTvShow(id: Int, newState: Boolean)
}