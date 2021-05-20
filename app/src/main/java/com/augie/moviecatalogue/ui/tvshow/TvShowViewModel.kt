package com.augie.moviecatalogue.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.augie.moviecatalogue.data.MovieEntity
import com.augie.moviecatalogue.data.MovieRepository
import com.augie.moviecatalogue.utils.DataDummy

class TvShowViewModel(private val repository: MovieRepository) : ViewModel() {
    fun getTvShows() : LiveData<List<MovieEntity>> = repository.getTvShows()
}