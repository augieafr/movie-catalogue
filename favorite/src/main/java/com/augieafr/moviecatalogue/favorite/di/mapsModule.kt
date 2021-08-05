package com.augieafr.moviecatalogue.favorite.di

import com.augieafr.moviecatalogue.favorite.movie.MovieFavoriteViewModel
import com.augieafr.moviecatalogue.favorite.tvshow.TvShowFavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mapsModule = module{
    viewModel { TvShowFavoriteViewModel(get()) }
    viewModel { MovieFavoriteViewModel(get()) }
}