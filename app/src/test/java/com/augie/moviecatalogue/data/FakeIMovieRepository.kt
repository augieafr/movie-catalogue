package com.augie.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.augie.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.augie.moviecatalogue.core.data.source.local.entity.TvShowEntity
import com.augie.moviecatalogue.core.utils.AppExecutors
import com.augie.moviecatalogue.core.data.Resource
import com.augie.moviecatalogue.core.data.source.remote.response.DetailMovieResponse
import com.augie.moviecatalogue.core.data.source.remote.response.DetailTvShowResponse
import com.augie.moviecatalogue.core.data.source.remote.response.MovieItem
import com.augie.moviecatalogue.core.data.source.remote.response.TvItem
import com.augie.moviecatalogue.core.data.source.remote.network.ApiResponse

class FakeIMovieRepository(
    private val remoteDataSource: com.augie.moviecatalogue.core.data.source.remote.RemoteDataSource,
    private val localDataSource: com.augie.moviecatalogue.core.data.source.local.LocalDataSource,
    private val appExecutors: AppExecutors
) : com.augie.moviecatalogue.core.data.MovieRepository {

    override fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            com.augie.moviecatalogue.core.data.NetworkBoundResource<PagedList<com.augie.moviecatalogue.core.data.source.local.entity.MovieEntity>, List<MovieItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<com.augie.moviecatalogue.core.data.source.local.entity.MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovie(), config).build()
            }

            override fun shouldFetch(data: PagedList<com.augie.moviecatalogue.core.data.source.local.entity.MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): LiveData<ApiResponse<List<MovieItem>>> =
                remoteDataSource.getMovies()

            override suspend fun saveCallResult(data: List<MovieItem>) {
                val movieList = ArrayList<com.augie.moviecatalogue.core.data.source.local.entity.MovieEntity>()
                for (item in data) {
                    val movie = com.augie.moviecatalogue.core.data.source.local.entity.MovieEntity(
                        id = item.id,
                        title = item.title,
                        overview = item.overview,
                        // take only the year
                        releaseDate = item.releaseDate.take(4),
                        genre = "",
                        duration = "",
                        poster = item.posterPath,
                        backdrop = item.backdropPath
                    )
                    movieList.add(movie)
                }
                localDataSource.insertMovie(movieList)
            }
        }.asLiveData()
    }

    override fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object : com.augie.moviecatalogue.core.data.NetworkBoundResource<PagedList<com.augie.moviecatalogue.core.data.source.local.entity.TvShowEntity>, List<TvItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<com.augie.moviecatalogue.core.data.source.local.entity.TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getTvShow(), config).build()
            }

            override fun shouldFetch(data: PagedList<com.augie.moviecatalogue.core.data.source.local.entity.TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): LiveData<ApiResponse<List<TvItem>>> =
                remoteDataSource.getTvShows()

            override suspend fun saveCallResult(data: List<TvItem>) {
                val tvShowsList = ArrayList<com.augie.moviecatalogue.core.data.source.local.entity.TvShowEntity>()
                for (item in data) {
                    val tvShow =
                        com.augie.moviecatalogue.core.data.source.local.entity.TvShowEntity(
                            id = item.id,
                            title = item.originalName,
                            overview = item.overview,
                            // take only the year
                            releaseDate = item.firstAirDate.take(4),
                            genre = "",
                            duration = "",
                            poster = item.posterPath,
                            backdrop = item.backdropPath
                        )
                    tvShowsList.add(tvShow)
                }
                localDataSource.insertTvShow(tvShowsList)
            }
        }.asLiveData()

    }

    override fun getDetailMovies(id: Int): LiveData<Resource<MovieEntity>> {
        return object : com.augie.moviecatalogue.core.data.NetworkBoundResource<com.augie.moviecatalogue.core.data.source.local.entity.MovieEntity, DetailMovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<com.augie.moviecatalogue.core.data.source.local.entity.MovieEntity> = localDataSource.getMovieById(id)

            override fun shouldFetch(data: com.augie.moviecatalogue.core.data.source.local.entity.MovieEntity?): Boolean =
                // if genre or duration empty string means this detail data never fetched before
                data == null || data.genre == "" || data.duration == ""

            override suspend fun createCall(): LiveData<ApiResponse<DetailMovieResponse>> =
                remoteDataSource.getDetailMovie(id)

            override suspend fun saveCallResult(data: DetailMovieResponse) {
                val listGenre = ArrayList<String>()
                for (genre in data.genres) {
                    listGenre.add(genre.name)
                }

                // convert runtime to hour minute format
                val duration = if (data.runtime > 60) {
                    val hour = data.runtime / 60
                    val minute = data.runtime % 60
                    "${hour}h ${minute}m"
                } else "${data.runtime}m"

                val movie = com.augie.moviecatalogue.core.data.source.local.entity.MovieEntity(
                    id = data.id,
                    title = data.title,
                    overview = data.overview,
                    // take only the year
                    releaseDate = data.releaseDate.take(4),
                    genre = listGenre.joinToString(", "),
                    duration = duration,
                    poster = data.posterPath,
                    backdrop = data.backdropPath
                )
                localDataSource.insertMovie(listOf(movie))
            }
        }.asLiveData()
    }

    override fun getDetailTvShow(id: Int): LiveData<Resource<TvShowEntity>> {
        return object : com.augie.moviecatalogue.core.data.NetworkBoundResource<com.augie.moviecatalogue.core.data.source.local.entity.TvShowEntity, DetailTvShowResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<com.augie.moviecatalogue.core.data.source.local.entity.TvShowEntity> = localDataSource.getTvShowById(id)

            override fun shouldFetch(data: com.augie.moviecatalogue.core.data.source.local.entity.TvShowEntity?): Boolean =
                // if genre empty string means this detail data never fetched before
                data == null || data.genre == ""

            override suspend fun createCall(): LiveData<ApiResponse<DetailTvShowResponse>> =
                remoteDataSource.getDetailTvShow(id)


            override suspend fun saveCallResult(data: DetailTvShowResponse) {
                val listGenre = ArrayList<String>()
                for (genre in data.genres) {
                    listGenre.add(genre.name)
                }

                // convert runtime to hour minute format
                val duration = if (data.episodeRunTime.isNotEmpty()) {
                    if (data.episodeRunTime[0] > 60) {
                        val hour = data.episodeRunTime[0] / 60
                        val minute = data.episodeRunTime[0] % 60
                        "${hour}h ${minute}m"
                    } else {
                        "${data.episodeRunTime[0]}m"
                    }
                } else {
                    ""
                }

                val tvShow = com.augie.moviecatalogue.core.data.source.local.entity.TvShowEntity(
                    id = data.id,
                    title = data.originalName,
                    overview = data.overview,
                    releaseDate = data.firstAirDate.take(4),
                    genre = listGenre.joinToString(", "),
                    duration = duration,
                    poster = data.posterPath,
                    backdrop = data.backdropPath
                )

                localDataSource.insertTvShow(listOf(tvShow))
            }
        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<PagedList<com.augie.moviecatalogue.core.data.source.local.entity.MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getMovieFavorite(), config).build()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<com.augie.moviecatalogue.core.data.source.local.entity.TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getTvShowFavorite(), config).build()
    }

    override fun setFavoriteMovie(id: Int, newState: Boolean) =
        localDataSource.setFavoriteMovie(id, newState)

    override fun setFavoriteTvShow(id: Int, newState: Boolean) =
        localDataSource.setFavoriteTvShow(id, newState)
}