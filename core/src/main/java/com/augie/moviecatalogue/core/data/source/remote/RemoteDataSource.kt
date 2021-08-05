package com.augie.moviecatalogue.core.data.source.remote

import com.augie.moviecatalogue.core.data.source.remote.network.ApiResponse
import com.augie.moviecatalogue.core.data.source.remote.network.ApiService
import com.augie.moviecatalogue.core.data.source.remote.response.DetailMovieResponse
import com.augie.moviecatalogue.core.data.source.remote.response.DetailTvShowResponse
import com.augie.moviecatalogue.core.data.source.remote.response.MovieItem
import com.augie.moviecatalogue.core.data.source.remote.response.TvItem
import com.augie.moviecatalogue.core.utils.EspressoIdlingResources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getMovies(): Flow<ApiResponse<List<MovieItem>>> {
        return flow {
            EspressoIdlingResources.increment()
            try {
                val response = apiService.getMovies()
                val dataArray = response.items
                if (dataArray.isNotEmpty()) {
                    EspressoIdlingResources.decrement()
                    emit(ApiResponse.Success(dataArray))
                } else {
                    EspressoIdlingResources.decrement()
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                EspressoIdlingResources.decrement()
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShows(): Flow<ApiResponse<List<TvItem>>> {
        return flow {
            EspressoIdlingResources.increment()
            try {
                val response = apiService.getTvShows()
                val dataArray = response.items
                if (dataArray.isNotEmpty()) {
                    EspressoIdlingResources.decrement()
                    emit(ApiResponse.Success(dataArray))
                } else {
                    EspressoIdlingResources.decrement()
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                EspressoIdlingResources.decrement()
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailMovie(id: Int): Flow<ApiResponse<DetailMovieResponse>> {
        return flow {
            EspressoIdlingResources.increment()
            try {
                val response = apiService.getDetailMovie(id)
                EspressoIdlingResources.decrement()
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                EspressoIdlingResources.decrement()
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailTvShow(id: Int): Flow<ApiResponse<DetailTvShowResponse>> {
        return flow {
            EspressoIdlingResources.increment()
            try {
                val response = apiService.getDetailTvShow(id)
                EspressoIdlingResources.decrement()
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                EspressoIdlingResources.decrement()
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}