package com.augie.moviecatalogue.injection

import com.augie.moviecatalogue.data.MovieRepository
import com.augie.moviecatalogue.data.source.remote.RemoteDataSource

object Injection {
    fun provideRepository(): MovieRepository {
        val remoteDataSource = RemoteDataSource.getInstance()

        return MovieRepository.getInstance(remoteDataSource)
    }
}