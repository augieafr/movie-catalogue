package com.augie.moviecatalogue.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.augie.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.augie.moviecatalogue.core.data.source.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("select * from movie_entity")
    fun getMovie(): Flow<List<MovieEntity>>

    @Query("select * from tvShow_entity")
    fun getTvShow(): Flow<List<TvShowEntity>>

    @Query("select * from movie_entity where favorite = 1")
    fun getMovieFavorite(): Flow<List<MovieEntity>>

    @Query("select * from tvShow_entity where favorite = 1")
    fun getTvShowFavorite(): Flow<List<TvShowEntity>>

    @Query("select * from movie_entity where id = :id")
    fun getMovieById(id: Int): Flow<MovieEntity>

    @Query("select * from tvShow_entity where id = :id")
    fun getTvShowById(id: Int): Flow<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(tvShows: List<TvShowEntity>)

    @Query("update movie_entity set favorite = :newState where id = :id")
    fun updateMovie(id: Int, newState: Boolean)

    @Query("update tvShow_entity set favorite = :newState where id = :id")
    fun updateTvShow(id: Int, newState: Boolean)
}