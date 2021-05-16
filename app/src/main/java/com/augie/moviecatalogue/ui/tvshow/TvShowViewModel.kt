package com.augie.moviecatalogue.ui.tvshow

import androidx.lifecycle.ViewModel
import com.augie.moviecatalogue.data.MovieEntity
import com.augie.moviecatalogue.utils.DataDummy

class TvShowViewModel : ViewModel() {
    fun getTvShow(): ArrayList<MovieEntity> = DataDummy.generateDummyTvShow()
}