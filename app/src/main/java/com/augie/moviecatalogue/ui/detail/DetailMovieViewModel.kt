package com.augie.moviecatalogue.ui.detail

import androidx.lifecycle.ViewModel
import com.augie.moviecatalogue.data.MovieEntity
import com.augie.moviecatalogue.utils.DataDummy

class DetailMovieViewModel : ViewModel() {
    private lateinit var movieId: String

    fun setSelectedMovie(id: Int) {
        movieId = id.toString()
    }

    fun getMovie(type: String): MovieEntity {
        lateinit var movie: MovieEntity
        lateinit var movieList: ArrayList<MovieEntity>

        when(type){
            DetailMovieActivity.MOVIE -> movieList = DataDummy.generateDummyMovie()
            DetailMovieActivity.TV_SHOW -> movieList = DataDummy.generateDummyTvShow()
        }

        for (item in movieList){
            if (movieId == item.id.toString()){
                movie = item
            }
        }
        return movie
    }
}