package com.augie.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.augie.moviecatalogue.data.source.local.entity.MovieEntity
import com.augie.moviecatalogue.data.source.local.entity.TvShowEntity
import com.augie.moviecatalogue.vo.Resource

interface MovieDataSource {
    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getDetailMovies(id: Int): LiveData<Resource<MovieEntity>>
    fun getDetailTvShow(id: Int): LiveData<Resource<TvShowEntity>>

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>>
    fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>>

    fun setFavoriteMovie(id: Int, newState: Boolean)
    fun setFavoriteTvShow(id: Int, newState: Boolean)

}