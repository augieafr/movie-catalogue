package com.augie.moviecatalogue.ui.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.augie.moviecatalogue.data.MovieEntity
import com.augie.moviecatalogue.data.MovieRepository
import com.augie.moviecatalogue.utils.DataDummy
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
    fun getMovie(): LiveData<List<MovieEntity>> = repository.getMovies()
}