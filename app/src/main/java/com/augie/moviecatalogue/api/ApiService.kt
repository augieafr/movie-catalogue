package com.augie.moviecatalogue.api

import com.augie.moviecatalogue.BuildConfig
import com.augie.moviecatalogue.data.source.remote.DetailMovieResponse
import com.augie.moviecatalogue.data.source.remote.DetailTvShowResponse
import com.augie.moviecatalogue.data.source.remote.MovieResponse
import com.augie.moviecatalogue.data.source.remote.TvResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/popular?api_key=${BuildConfig.API_TOKEN}")
    fun getPopularMovies() : Call<MovieResponse>

    @GET("tv/popular?api_key=${BuildConfig.API_TOKEN}")
    fun getPopularTvShows() : Call<TvResponse>

    @GET("movie/{id}?api_key=${BuildConfig.API_TOKEN}")
    fun getDetailMovie(
        @Path("id") id: Int
    ) : Call<DetailMovieResponse>

    @GET("tv/{id}?api_key=${BuildConfig.API_TOKEN}")
    fun getDetailTvShow(
        @Path("id") id: Int
    ) : Call<DetailTvShowResponse>
}