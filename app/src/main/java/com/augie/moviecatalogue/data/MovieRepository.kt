package com.augie.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.augie.moviecatalogue.data.source.local.LocalDataSource
import com.augie.moviecatalogue.data.source.local.entity.MovieEntity
import com.augie.moviecatalogue.data.source.local.entity.TvShowEntity
import com.augie.moviecatalogue.data.source.remote.*
import com.augie.moviecatalogue.utils.AppExecutors
import com.augie.moviecatalogue.vo.Resource

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovieDataSource {

    override fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovie(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieItem>>> =
                remoteDataSource.getPopularMovies()

            override fun saveCallResult(data: List<MovieItem>) {
                val movieList = ArrayList<MovieEntity>()
                for (item in data) {
                    val movie = MovieEntity(
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
        return object : NetworkBoundResource<PagedList<TvShowEntity>, List<TvItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getTvShow(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvItem>>> =
                remoteDataSource.getPopularTvShows()

            override fun saveCallResult(data: List<TvItem>) {
                val tvShowsList = ArrayList<TvShowEntity>()
                for (item in data) {
                    val tvShow = TvShowEntity(
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
        return object : NetworkBoundResource<MovieEntity, DetailMovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> = localDataSource.getMovieById(id)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                // if genre or duration empty string means this detail data never fetched before
                data == null || data.genre == "" || data.duration == ""

            override fun createCall(): LiveData<ApiResponse<DetailMovieResponse>> =
                remoteDataSource.getDetailMovie(id)

            override fun saveCallResult(data: DetailMovieResponse) {
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

                val movie = MovieEntity(
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
        return object : NetworkBoundResource<TvShowEntity, DetailTvShowResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowEntity> = localDataSource.getTvShowById(id)

            override fun shouldFetch(data: TvShowEntity?): Boolean =
                // if genre empty string means this detail data never fetched before
                data == null || data.genre == ""

            override fun createCall(): LiveData<ApiResponse<DetailTvShowResponse>> =
                remoteDataSource.getDetailTvShow(id)


            override fun saveCallResult(data: DetailTvShowResponse) {
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

                val tvShow = TvShowEntity(
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

    override fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getMovieFavorite(), config).build()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getTvShowFavorite(), config).build()
    }

    override fun setFavoriteMovie(id: Int, newState: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(id, newState) }

    override fun setFavoriteTvShow(id: Int, newState: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setFavoriteTvShow(id, newState) }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(
                    remoteDataSource,
                    localDataSource,
                    appExecutors
                ).apply { instance = this }
            }
    }
}