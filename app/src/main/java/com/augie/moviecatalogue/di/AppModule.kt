package com.augie.moviecatalogue.di

import com.augie.moviecatalogue.core.domain.usecase.MovieInteractor
import com.augie.moviecatalogue.core.domain.usecase.MovieUseCase
import com.augie.moviecatalogue.detail.DetailViewModel
import com.augie.moviecatalogue.home.movie.MovieViewModel
import com.augie.moviecatalogue.home.tvshow.TvShowViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
