package com.augie.moviecatalogue.ui.home.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.augie.moviecatalogue.data.MovieRepository
import com.augie.moviecatalogue.data.source.local.entity.TvShowEntity
import com.augie.moviecatalogue.vo.Resource

class TvShowViewModel(private val repository: MovieRepository) : ViewModel() {
    fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> = repository.getTvShows()
}