package com.augie.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.augie.moviecatalogue.data.MovieEntity
import com.augie.moviecatalogue.data.MovieRepository
import com.augie.moviecatalogue.utils.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {
    private lateinit var viewModel: DetailMovieViewModel
    private val dummyMovie = DataDummy.generateDummyMovie()[0]
    private val dummyMovieId = dummyMovie.id

    private val dummyTvShow = DataDummy.generateDummyTvShow()[0]
    private val dummyTvShowId = dummyTvShow.id

    private val typeMovie = DetailMovieActivity.MOVIE
    private val typeTvShow = DetailMovieActivity.TV_SHOW

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var detailObserver: Observer<MovieEntity>

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(movieRepository)
    }


    @Test
    fun testGetMovieDetail() {
        val movie = MutableLiveData<MovieEntity>()
        movie.value = dummyMovie

        `when`(movieRepository.getDetailMovies(dummyMovieId)).thenReturn(movie)
        val movieEntity = viewModel.getMovie(typeMovie, dummyMovieId).value as MovieEntity
        verify(movieRepository).getDetailMovies(dummyMovieId)
        assertNotNull(movieEntity)
        assertEquals(dummyMovie.title, movieEntity.title)
        assertEquals(dummyMovie.overview, movieEntity.overview)
        assertEquals(dummyMovie.releaseDate, movieEntity.releaseDate)
        assertEquals(dummyMovie.genre, movieEntity.genre)
        assertEquals(dummyMovie.duration, movieEntity.duration)
        assertEquals(dummyMovie.id, movieEntity.id)
        assertEquals(dummyMovie.poster, movieEntity.poster)
        assertEquals(dummyMovie.backdrop, movieEntity.backdrop)

        viewModel.getMovie(typeMovie, dummyMovieId).observeForever(detailObserver)
        verify(detailObserver).onChanged(dummyMovie)
    }

    @Test
    fun testGetTvShowDetail() {
        val tvShow = MutableLiveData<MovieEntity>()
        tvShow.value = dummyTvShow

        `when`(movieRepository.getDetailTvShow(dummyTvShowId)).thenReturn(tvShow)
        val tvShowEntity = viewModel.getMovie(typeTvShow, dummyTvShowId).value as MovieEntity
        verify(movieRepository).getDetailTvShow(dummyTvShowId)
        assertNotNull(tvShowEntity)
        assertEquals(dummyTvShow.title, tvShowEntity.title)
        assertEquals(dummyTvShow.overview, tvShowEntity.overview)
        assertEquals(dummyTvShow.releaseDate, tvShowEntity.releaseDate)
        assertEquals(dummyTvShow.genre, tvShowEntity.genre)
        assertEquals(dummyTvShow.duration, tvShowEntity.duration)
        assertEquals(dummyTvShow.id, tvShowEntity.id)
        assertEquals(dummyTvShow.poster, tvShowEntity.poster)
        assertEquals(dummyTvShow.backdrop, tvShowEntity.backdrop)

        viewModel.getMovie(typeTvShow, dummyTvShowId).observeForever(detailObserver)
        verify(detailObserver).onChanged(dummyTvShow)
    }
}