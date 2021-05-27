package com.augie.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.augie.moviecatalogue.data.source.local.LocalDataSource
import com.augie.moviecatalogue.data.source.local.entity.MovieEntity
import com.augie.moviecatalogue.data.source.local.entity.TvShowEntity
import com.augie.moviecatalogue.data.source.remote.RemoteDataSource
import com.augie.moviecatalogue.utils.AppExecutors
import com.augie.moviecatalogue.utils.DataDummy
import com.augie.moviecatalogue.utils.LiveDataTestUtils
import com.augie.moviecatalogue.utils.PagedListUtil
import com.augie.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieRepositoryTest {

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val movieRepository = FakeMovieRepository(remote, local, appExecutors)

    private val dummyMovie = DataDummy.generateDummyMovie()
    private val dummyTvShow = DataDummy.generateDummyTvShow()

    private val detailMovie = DataDummy.generateDummyMovie()[0]
    private val detailTvShow = DataDummy.generateDummyTvShow()[0]

    private val movieId = detailMovie.id
    private val tvShowId = detailTvShow.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testGetMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getMovie()).thenReturn(dataSourceFactory)
        movieRepository.getMovies()

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovie()))
        verify(local).getMovie()
        assertNotNull(movieEntities.data)
        assertEquals(dummyMovie.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun testGetTvShows() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getTvShow()).thenReturn(dataSourceFactory)
        movieRepository.getTvShows()

        val tvShowEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShow()))
        verify(local).getTvShow()
        assertNotNull(tvShowEntities.data)
        assertEquals(dummyTvShow.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun testGetDetailMovies() {
        val dummyDetail = MutableLiveData<MovieEntity>()
        dummyDetail.value = detailMovie
        `when`(local.getMovieById(movieId)).thenReturn(dummyDetail)

        val movieDetailEntity = LiveDataTestUtils.getValue(movieRepository.getDetailMovies(movieId))
        verify(local).getMovieById(movieId)
        assertNotNull(movieDetailEntity)
        assertEquals(detailMovie.id, movieDetailEntity.data?.id)
    }

    @Test
    fun testGetDetailTvShow() {
        val dummyDetail = MutableLiveData<TvShowEntity>()
        dummyDetail.value = detailTvShow
        `when`(local.getTvShowById(tvShowId)).thenReturn(dummyDetail)

        val tvShowDetailEntity =
            LiveDataTestUtils.getValue(movieRepository.getDetailTvShow(movieId))
        verify(local).getTvShowById(movieId)
        assertNotNull(tvShowDetailEntity)
        assertEquals(detailTvShow.id, tvShowDetailEntity.data?.id)
    }

    @Test
    fun testGetFavoriteMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getMovieFavorite()).thenReturn(dataSourceFactory)
        movieRepository.getFavoriteMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(dummyMovie))
        verify(local).getMovieFavorite()
        assertNotNull(movieEntities)
        assertEquals(dummyMovie.size, movieEntities.data?.size)
    }

    @Test
    fun testGetFavoriteTvShows() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getTvShowFavorite()).thenReturn(dataSourceFactory)
        movieRepository.getFavoriteTvShows()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(dummyTvShow))
        verify(local).getTvShowFavorite()
        assertNotNull(tvShowEntities)
        assertEquals(dummyTvShow.size, tvShowEntities.data?.size)
    }

    @Test
    fun testSetFavoriteMovie() {
        movieRepository.setFavoriteMovie(detailMovie.id, true)
        verify(local).setFavoriteMovie(detailMovie.id, true)
        verifyNoMoreInteractions(local)
    }

    @Test
    fun testSetFavoriteTvShow() {
        movieRepository.setFavoriteTvShow(detailTvShow.id, true)
        verify(local).setFavoriteTvShow(detailTvShow.id, true)
        verifyNoMoreInteractions(local)
    }
}