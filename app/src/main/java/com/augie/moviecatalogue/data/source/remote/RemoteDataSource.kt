package com.augie.moviecatalogue.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.augie.moviecatalogue.api.ApiConfig
import com.augie.moviecatalogue.utils.EspressoIdlingResources
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    fun getPopularMovies(): LiveData<ApiResponse<List<MovieItem>>> {
        EspressoIdlingResources.increment()
        val client = getApiClient()
        val movieResponse = MutableLiveData<ApiResponse<List<MovieItem>>>()
        client.getPopularMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                movieResponse.value = ApiResponse.success(response.body()?.items as List<MovieItem>)
                EspressoIdlingResources.decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
                EspressoIdlingResources.decrement()
            }
        })

        return movieResponse
    }

    fun getPopularTvShows(): LiveData<ApiResponse<List<TvItem>>> {
        EspressoIdlingResources.increment()
        val client = getApiClient()
        val tvResponses = MutableLiveData<ApiResponse<List<TvItem>>>()
        client.getPopularTvShows().enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                tvResponses.value = ApiResponse.success(response.body()?.items as List<TvItem>)
                EspressoIdlingResources.decrement()
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
                EspressoIdlingResources.decrement()
            }
        })

        return tvResponses
    }

    fun getDetailMovie(id: Int): LiveData<ApiResponse<DetailMovieResponse>> {
        EspressoIdlingResources.increment()
        val client = getApiClient()
        val detailMovie = MutableLiveData<ApiResponse<DetailMovieResponse>>()
        client.getDetailMovie(id).enqueue(object : Callback<DetailMovieResponse> {
            override fun onResponse(
                call: Call<DetailMovieResponse>,
                response: Response<DetailMovieResponse>
            ) {
                detailMovie.value = ApiResponse.success(response.body() as DetailMovieResponse)
                EspressoIdlingResources.decrement()
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
                EspressoIdlingResources.decrement()
            }
        })

        return detailMovie
    }

    fun getDetailTvShow(id: Int): LiveData<ApiResponse<DetailTvShowResponse>> {
        EspressoIdlingResources.increment()
        val client = getApiClient()
        val tvShowDetail = MutableLiveData<ApiResponse<DetailTvShowResponse>>()

        client.getDetailTvShow(id).enqueue(object : Callback<DetailTvShowResponse> {
            override fun onResponse(
                call: Call<DetailTvShowResponse>,
                response: Response<DetailTvShowResponse>
            ) {
                tvShowDetail.value = ApiResponse.success(response.body() as DetailTvShowResponse)
                EspressoIdlingResources.decrement()
            }

            override fun onFailure(call: Call<DetailTvShowResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message.toString()}")
                EspressoIdlingResources.decrement()
            }

        })

        return tvShowDetail
    }

    private fun getApiClient() = ApiConfig.getApiService()


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