package com.augie.moviecatalogue.core.di

import androidx.room.Room
import com.augie.moviecatalogue.core.data.MovieRepository
import com.augie.moviecatalogue.core.data.source.local.LocalDataSource
import com.augie.moviecatalogue.core.data.source.local.room.MovieCatalogueDatabase
import com.augie.moviecatalogue.core.data.source.remote.RemoteDataSource
import com.augie.moviecatalogue.core.data.source.remote.network.ApiService
import com.augie.moviecatalogue.core.domain.repository.IMovieRepository
import com.augie.moviecatalogue.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    factory { get<MovieCatalogueDatabase>().movieDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieCatalogueDatabase::class.java,
            "MovieCatalogue.db"
        ).build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieRepository> { MovieRepository(get(), get(), get()) }
}