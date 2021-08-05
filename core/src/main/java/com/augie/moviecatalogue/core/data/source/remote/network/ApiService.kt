package com.augie.moviecatalogue.core.data.source.remote.network
import com.augie.moviecatalogue.core.BuildConfig
import com.augie.moviecatalogue.core.data.source.remote.response.DetailMovieResponse
import com.augie.moviecatalogue.core.data.source.remote.response.DetailTvShowResponse
import com.augie.moviecatalogue.core.data.source.remote.response.MovieResponse
import com.augie.moviecatalogue.core.data.source.remote.response.TvResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/top_rated?api_key=${BuildConfig.API_TOKEN}")
    suspend fun getMovies(): MovieResponse

    @GET("tv/top_rated?api_key=${BuildConfig.API_TOKEN}")
    suspend fun getTvShows(): TvResponse

    @GET("movie/{id}?api_key=${BuildConfig.API_TOKEN}")
    suspend fun getDetailMovie(
        @Path("id") id: Int
    ): DetailMovieResponse

    @GET("tv/{id}?api_key=${BuildConfig.API_TOKEN}")
    suspend fun getDetailTvShow(
        @Path("id") id: Int
    ): DetailTvShowResponse
}