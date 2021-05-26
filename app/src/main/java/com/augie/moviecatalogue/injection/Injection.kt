package com.augie.moviecatalogue.injection

import android.content.Context
import com.augie.moviecatalogue.data.MovieRepository
import com.augie.moviecatalogue.data.source.local.LocalDataSource
import com.augie.moviecatalogue.data.source.local.room.MovieCatalogueDatabase
import com.augie.moviecatalogue.data.source.remote.RemoteDataSource
import com.augie.moviecatalogue.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MovieRepository {
        val database = MovieCatalogueDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()

        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}