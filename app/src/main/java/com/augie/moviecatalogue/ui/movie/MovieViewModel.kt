package com.augie.moviecatalogue.ui.movie

import androidx.lifecycle.ViewModel
import com.augie.moviecatalogue.data.MovieEntity
import com.augie.moviecatalogue.utils.DataDummy

class MovieViewModel : ViewModel() {
    fun getMovie(): ArrayList<MovieEntity> = DataDummy.generateDummyMovie()
}