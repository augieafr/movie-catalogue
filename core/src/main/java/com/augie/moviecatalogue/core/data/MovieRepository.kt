package com.augie.moviecatalogue.core.data

import com.augie.moviecatalogue.core.data.source.local.LocalDataSource
import com.augie.moviecatalogue.core.data.source.remote.RemoteDataSource
import com.augie.moviecatalogue.core.data.source.remote.network.ApiResponse
import com.augie.moviecatalogue.core.data.source.remote.response.DetailMovieResponse
import com.augie.moviecatalogue.core.data.source.remote.response.DetailTvShowResponse
import com.augie.moviecatalogue.core.data.source.remote.response.MovieItem
import com.augie.moviecatalogue.core.data.source.remote.response.TvItem
import com.augie.moviecatalogue.core.domain.model.Movie
import com.augie.moviecatalogue.core.domain.model.TvShow
import com.augie.moviecatalogue.core.domain.repository.IMovieRepository
import com.augie.moviecatalogue.core.utils.AppExecutors
import com.augie.moviecatalogue.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getMovies(): Flow<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<MovieItem>>(appExecutors) {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getMovie().map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MovieItem>>> =
                remoteDataSource.getMovies()

            override suspend fun saveCallResult(data: List<MovieItem>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()
    }

    override fun getTvShows(): Flow<Resource<List<TvShow>>> {
        return object : NetworkBoundResource<List<TvShow>, List<TvItem>>(appExecutors) {
            override fun loadFromDB(): Flow<List<TvShow>> {
                return localDataSource.getTvShow().map {
                    DataMapper.mapTvShowEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<TvShow>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TvItem>>> =
                remoteDataSource.getTvShows()

            override suspend fun saveCallResult(data: List<TvItem>) {
                val tvShowsList = DataMapper.mapTvShowResponsesToEntities(data)
                localDataSource.insertTvShow(tvShowsList)
            }
        }.asFlow()

    }

    override fun getDetailMovies(id: Int): Flow<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, DetailMovieResponse>(appExecutors) {
            override fun loadFromDB(): Flow<Movie> = localDataSource.getMovieById(id).map {
                DataMapper.mapMovieEntityToDomain(it)
            }

            override fun shouldFetch(data: Movie?): Boolean =
            // if genre or duration empty string means this detail data never fetched before
                // because we only need genre and duration data from detail api
                data == null || data.genre == "" || data.duration == ""

            override suspend fun createCall(): Flow<ApiResponse<DetailMovieResponse>> =
                remoteDataSource.getDetailMovie(id)

            override suspend fun saveCallResult(data: DetailMovieResponse) {
                val movie = DataMapper.mapDetailMovieResponseToEntity(data)
                localDataSource.insertMovie(listOf(movie))
            }
        }.asFlow()
    }

    override fun getDetailTvShow(id: Int): Flow<Resource<TvShow>> {
        return object : NetworkBoundResource<TvShow, DetailTvShowResponse>(appExecutors) {
            override fun loadFromDB(): Flow<TvShow> = localDataSource.getTvShowById(id).map {
                DataMapper.mapDetailTvShowsEntityToDomain(it)
            }

            override fun shouldFetch(data: TvShow?): Boolean =
            // if genre empty string means this detail data never fetched before
                // because we only need genre and duration data from detail api
                data == null || data.genre == ""

            override suspend fun createCall(): Flow<ApiResponse<DetailTvShowResponse>> =
                remoteDataSource.getDetailTvShow(id)


            override suspend fun saveCallResult(data: DetailTvShowResponse) {
                val tvShow = DataMapper.mapDetailTvShowsResponseToEntity(data)

                localDataSource.insertTvShow(listOf(tvShow))
            }
        }.asFlow()
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> =
        localDataSource.getMovieFavorite().map {
            DataMapper.mapMovieEntitiesToDomain(it)
        }

    override fun getFavoriteTvShows(): Flow<List<TvShow>> =
        localDataSource.getTvShowFavorite().map {
            DataMapper.mapTvShowEntitiesToDomain(it)
        }

    override fun setFavoriteMovie(id: Int, newState: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(id, newState) }

    override fun setFavoriteTvShow(id: Int, newState: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setFavoriteTvShow(id, newState) }
}