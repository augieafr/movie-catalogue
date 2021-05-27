package com.augie.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.augie.moviecatalogue.data.MovieRepository
import com.augie.moviecatalogue.data.source.local.entity.MovieEntity
import com.augie.moviecatalogue.data.source.local.entity.TvShowEntity
import com.augie.moviecatalogue.utils.DataDummy
import com.augie.moviecatalogue.vo.Resource
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
    private lateinit var detailMovieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var detailTvShowObserver: Observer<Resource<TvShowEntity>>

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(movieRepository)
    }


    @Test
    fun testGetMovieDetail() {
        val dummyDetailMovie = Resource.success(dummyMovie)
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyDetailMovie

        `when`(movieRepository.getDetailMovies(dummyMovieId)).thenReturn(movie)
        val movieEntity = viewModel.getMovie(dummyMovieId).value as Resource<MovieEntity>
        verify(movieRepository).getDetailMovies(dummyMovieId)
        assertNotNull(movieEntity)
        assertEquals(dummyMovie.title, movieEntity.data?.title)
        assertEquals(dummyMovie.overview, movieEntity.data?.overview)
        assertEquals(dummyMovie.releaseDate, movieEntity.data?.releaseDate)
        assertEquals(dummyMovie.genre, movieEntity.data?.genre)
        assertEquals(dummyMovie.duration, movieEntity.data?.duration)
        assertEquals(dummyMovie.id, movieEntity.data?.id)
        assertEquals(dummyMovie.poster, movieEntity.data?.poster)
        assertEquals(dummyMovie.backdrop, movieEntity.data?.backdrop)

        viewModel.getMovie(dummyMovieId).observeForever(detailMovieObserver)
        verify(detailMovieObserver).onChanged(dummyDetailMovie)
    }

    @Test
    fun testGetTvShowDetail() {
        val detailTvShow = Resource.success(dummyTvShow)
        val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        tvShow.value = detailTvShow

        `when`(movieRepository.getDetailTvShow(dummyTvShowId)).thenReturn(tvShow)
        val tvShowEntity = viewModel.getTvShow(dummyTvShowId).value as Resource<TvShowEntity>
        verify(movieRepository).getDetailTvShow(dummyTvShowId)
        assertNotNull(tvShowEntity)
        assertEquals(dummyTvShow.title, tvShowEntity.data?.title)
        assertEquals(dummyTvShow.overview, tvShowEntity.data?.overview)
        assertEquals(dummyTvShow.releaseDate, tvShowEntity.data?.releaseDate)
        assertEquals(dummyTvShow.genre, tvShowEntity.data?.genre)
        assertEquals(dummyTvShow.duration, tvShowEntity.data?.duration)
        assertEquals(dummyTvShow.id, tvShowEntity.data?.id)
        assertEquals(dummyTvShow.poster, tvShowEntity.data?.poster)
        assertEquals(dummyTvShow.backdrop, tvShowEntity.data?.backdrop)

        viewModel.getTvShow(dummyTvShowId).observeForever(detailTvShowObserver)
        verify(detailTvShowObserver).onChanged(detailTvShow)
    }

    @Test
    fun testSetFavoriteMovie() {
        viewModel.setFavorite(dummyMovieId, true, typeMovie)
        verify(movieRepository).setFavoriteMovie(dummyMovieId, true)
    }

    @Test
    fun testSetFavoriteTvShow() {
        viewModel.setFavorite(dummyTvShowId, true, typeTvShow)
        verify(movieRepository).setFavoriteTvShow(dummyTvShowId, true)
    }
}