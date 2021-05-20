package com.augie.moviecatalogue.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.augie.moviecatalogue.data.source.remote.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class FakeMovieRepository(private val remoteDataSource: RemoteDataSource) : MovieDataSource{

    override fun getMovies(): LiveData<List<MovieEntity>> {
        val moviesLiveData = MutableLiveData<List<MovieEntity>>()
        CoroutineScope(IO).launch {
            remoteDataSource.getPopularMovies(object : RemoteDataSource.LoadPopularMoviesCallback {
                override fun onAllPopularMoviesReceived(listMovie: List<MovieItem>) {
                    val moviesList = ArrayList<MovieEntity>()
                    // convert data from response(MovieItem) to entity(MovieEntity)
                    for (item in listMovie) {
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
                        moviesList.add(movie)

                    }
                    moviesLiveData.postValue(moviesList)
                }

            })
        }
        return moviesLiveData
    }

    override fun getTvShows(): LiveData<List<MovieEntity>> {
        val tvShowsLiveData = MutableLiveData<List<MovieEntity>>()
        remoteDataSource.getPopularTvShows(object : RemoteDataSource.LoadPopularTvShowsCallback{
            override fun onAllPopularTvShowsReceived(listTvShow: List<TvItem>) {
                // convert data from response(TvItem) to entity(MovieEntity)
                val tvShowsList = ArrayList<MovieEntity>()
                for (item in listTvShow) {
                    val movie = MovieEntity(
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
                    tvShowsList.add(movie)
                }
                tvShowsLiveData.postValue(tvShowsList)
            }
        })
        return tvShowsLiveData
    }

    override fun getDetailMovies(id: Int): LiveData<MovieEntity> {
        val detailMovieLiveData = MutableLiveData<MovieEntity>()
        remoteDataSource.getDetailMovie(id, object : RemoteDataSource.LoadDetailMovie {
            override fun onDetailMovieReceived(detailMovie: DetailMovieResponse) {
                // convert DetailResponseMovie to MovieEntity
                val listGenre = ArrayList<String>()
                for (genre in detailMovie.genres) {
                    listGenre.add(genre.name)
                }

                // convert runtime to hour minute format
                val duration = if (detailMovie.runtime > 60){
                    val hour = detailMovie.runtime / 60
                    val minute = detailMovie.runtime % 60
                    "${hour}h ${minute}m"
                } else "${detailMovie.runtime}m"

                val movie = MovieEntity(
                    id = detailMovie.id,
                    title = detailMovie.title,
                    overview = detailMovie.overview,
                    // take only the year
                    releaseDate = detailMovie.releaseDate.take(4),
                    genre = listGenre.joinToString(", "),
                    duration = duration,
                    poster = detailMovie.posterPath,
                    backdrop = detailMovie.backdropPath
                )
                detailMovieLiveData.postValue(movie)
            }
        })
        return detailMovieLiveData
    }

    override fun getDetailTvShow(id: Int): LiveData<MovieEntity> {
        val detailTvShowLiveData = MutableLiveData<MovieEntity>()
        remoteDataSource.getDetailTvShow(id, object : RemoteDataSource.LoadDetailTvShow {
            override fun onDetailTvShowReceived(detailTvShow: DetailTvShowResponse) {
                // convert from DetailTvResponse to MovieEntity
                val listGenre = ArrayList<String>()
                for (genre in detailTvShow.genres) {
                    listGenre.add(genre.name)
                }

                // convert runtime to hour minute format
                val duration = if (detailTvShow.episodeRunTime.isNotEmpty()){
                    if (detailTvShow.episodeRunTime[0] >60){
                        val hour = detailTvShow.episodeRunTime[0] / 60
                        val minute = detailTvShow.episodeRunTime[0] % 60
                        "${hour}h ${minute}m"
                    } else {
                        "${detailTvShow.episodeRunTime[0]}m"
                    }
                } else {
                    " "
                }

                val tvShow = MovieEntity(
                    id = detailTvShow.id,
                    title = detailTvShow.originalName,
                    overview = detailTvShow.overview,
                    releaseDate = detailTvShow.firstAirDate,
                    genre = listGenre.joinToString(", "),
                    duration = duration,
                    poster = detailTvShow.posterPath,
                    backdrop = detailTvShow.backdropPath
                )
                detailTvShowLiveData.postValue(tvShow)
            }
        })
        return detailTvShowLiveData
    }
}