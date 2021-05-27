package com.augie.moviecatalogue.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.augie.moviecatalogue.data.MovieRepository
import com.augie.moviecatalogue.injection.Injection
import com.augie.moviecatalogue.ui.detail.DetailMovieViewModel
import com.augie.moviecatalogue.ui.favorite.movie.MovieFavoriteViewModel
import com.augie.moviecatalogue.ui.favorite.tvshow.TvShowFavoriteViewModel
import com.augie.moviecatalogue.ui.home.movie.MovieViewModel
import com.augie.moviecatalogue.ui.home.tvshow.TvShowViewModel

class ViewModelFactory private constructor(private val mMovieRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                return MovieViewModel(mMovieRepository) as T
            }

            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                return DetailMovieViewModel(mMovieRepository) as T
            }

            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                return TvShowViewModel(mMovieRepository) as T
            }

            modelClass.isAssignableFrom(MovieFavoriteViewModel::class.java) -> {
                return MovieFavoriteViewModel(mMovieRepository) as T
            }

            modelClass.isAssignableFrom(TvShowFavoriteViewModel::class.java) -> {
                return TvShowFavoriteViewModel(mMovieRepository) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }
}