package com.augie.moviecatalogue.core.domain.usecase

import com.augie.moviecatalogue.core.data.Resource
import com.augie.moviecatalogue.core.domain.model.Movie
import com.augie.moviecatalogue.core.domain.model.TvShow
import com.augie.moviecatalogue.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {
    override fun getMovies(): Flow<Resource<List<Movie>>> =
        movieRepository.getMovies()

    override fun getTvShows(): Flow<Resource<List<TvShow>>> =
        movieRepository.getTvShows()

    override fun getDetailMovies(id: Int): Flow<Resource<Movie>> =
        movieRepository.getDetailMovies(id)

    override fun getDetailTvShow(id: Int): Flow<Resource<TvShow>> =
        movieRepository.getDetailTvShow(id)

    override fun getFavoriteMovies(): Flow<List<Movie>> =
        movieRepository.getFavoriteMovies()

    override fun getFavoriteTvShows(): Flow<List<TvShow>> =
        movieRepository.getFavoriteTvShows()

    override fun setFavoriteMovie(id: Int, newState: Boolean) =
        movieRepository.setFavoriteMovie(id, newState)

    override fun setFavoriteTvShow(id: Int, newState: Boolean) =
        movieRepository.setFavoriteTvShow(id, newState)
}