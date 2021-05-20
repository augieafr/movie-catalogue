package com.augie.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.augie.moviecatalogue.data.source.remote.RemoteDataSource
import com.augie.moviecatalogue.utils.DataDummy
import com.augie.moviecatalogue.utils.LiveDataTestUtils
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MovieRepositoryTest {

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val movieRepository = FakeMovieRepository(remote)

    private val movieResponse = DataDummy.generateDummyMovieResponse().items
    private val tvShowResponse = DataDummy.generateDummyTvShowResponse().items

    private val detailMovieResponse = DataDummy.generateDetailMovie()
    private val detailTvShowResponse = DataDummy.generateDetailTvShow()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testGetMovies() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadPopularMoviesCallback)
                .onAllPopularMoviesReceived(movieResponse)
            null
        }.`when`(remote).getPopularMovies(any())
        val movieEntities = LiveDataTestUtils.getValue(movieRepository.getMovies())
        verify(remote).getPopularMovies(any())
        assertNotNull(movieEntities)
        assertEquals(movieResponse.size.toLong(), movieEntities.size.toLong())
    }

    @Test
    fun testGetTvShows() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadPopularTvShowsCallback)
                .onAllPopularTvShowsReceived(tvShowResponse)
            null
        }.`when`(remote).getPopularTvShows(any())
        val tvShowEntities = LiveDataTestUtils.getValue(movieRepository.getTvShows())
        verify(remote).getPopularTvShows(any())
        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponse.size.toLong(), tvShowEntities.size.toLong())
    }

    @Test
    fun testGetDetailMovies() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadDetailMovie)
                .onDetailMovieReceived(detailMovieResponse)
            null
        }.`when`(remote).getDetailMovie(eq(1), any())
        val movie = LiveDataTestUtils.getValue(movieRepository.getDetailMovies(1))
        verify(remote).getDetailMovie(eq(1), any())
        assertNotNull(movie)
        assertEquals(detailMovieResponse.id, movie.id)
    }

    @Test
    fun testGetDetailTvShow() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadDetailTvShow)
                .onDetailTvShowReceived(detailTvShowResponse)
            null
        }.`when`(remote).getDetailTvShow(eq(1), any())
        val tvShow = LiveDataTestUtils.getValue(movieRepository.getDetailTvShow(1))
        verify(remote).getDetailTvShow(eq(1), any())
        assertNotNull(tvShow)
        assertEquals(detailTvShowResponse.id, tvShow.id)
    }
}