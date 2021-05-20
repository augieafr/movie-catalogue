package com.augie.moviecatalogue.data.source.remote

import android.util.Log
import com.augie.moviecatalogue.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    fun getPopularMovies(callback: LoadPopularMoviesCallback) {
        val client = getApiClient()
        client.getPopularMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    response.body()?.items?.let { listMovie ->
                        callback.onAllPopularMoviesReceived(
                            listMovie
                        )
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getPopularTvShows(callback: LoadPopularTvShowsCallback) {
        val client = getApiClient()
        client.getPopularTvShows().enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                if (response.isSuccessful) {
                    response.body()?.items?.let { listTvShow ->
                        callback.onAllPopularTvShowsReceived(
                            listTvShow
                        )
                    }
                }
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDetailMovie(id: Int, callback: LoadDetailMovie) {
        val client = getApiClient()
        client.getDetailMovie(id).enqueue(object : Callback<DetailMovieResponse> {
            override fun onResponse(
                call: Call<DetailMovieResponse>,
                response: Response<DetailMovieResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { detailMovie ->
                        callback.onDetailMovieReceived(
                            detailMovie
                        )
                    }
                }
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDetailTvShow(id: Int, callback: LoadDetailTvShow) {
        val client = getApiClient()
        client.getDetailTvShow(id).enqueue(object : Callback<DetailTvShowResponse> {
            override fun onResponse(
                call: Call<DetailTvShowResponse>,
                response: Response<DetailTvShowResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { tvShowDetail ->
                        callback.onDetailTvShowReceived(
                            tvShowDetail
                        )
                    }
                }
            }

            override fun onFailure(call: Call<DetailTvShowResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    private fun getApiClient() = ApiConfig.getApiService()

    interface LoadPopularMoviesCallback {
        fun onAllPopularMoviesReceived(listMovie: List<MovieItem>)
    }

    interface LoadPopularTvShowsCallback {
        fun onAllPopularTvShowsReceived(listTvShow: List<TvItem>)
    }

    interface LoadDetailMovie {
        fun onDetailMovieReceived(detailMovie: DetailMovieResponse)
    }

    interface LoadDetailTvShow {
        fun onDetailTvShowReceived(detailTvShow: DetailTvShowResponse)
    }

    companion object {
        private const val TAG = "RemoteDataSource"

        @Volatile
        private var instance: RemoteDataSource? = null
        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }
}