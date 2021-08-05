package com.augie.moviecatalogue.home.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.augie.moviecatalogue.core.data.Resource
import com.augie.moviecatalogue.core.domain.model.TvShow
import com.augie.moviecatalogue.core.domain.usecase.MovieUseCase

class TvShowViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun getTvShows(): LiveData<Resource<List<TvShow>>> = movieUseCase.getTvShows().asLiveData()
}