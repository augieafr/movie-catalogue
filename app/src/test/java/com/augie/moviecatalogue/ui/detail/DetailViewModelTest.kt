package com.augie.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.augie.moviecatalogue.core.data.Resource
import com.augie.moviecatalogue.core.domain.model.Movie
import com.augie.moviecatalogue.core.domain.model.TvShow
import com.augie.moviecatalogue.core.domain.usecase.MovieInteractor
import com.augie.moviecatalogue.core.utils.DataDummy
import com.augie.moviecatalogue.detail.DetailActivity
import com.augie.moviecatalogue.detail.DetailViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
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
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private val dummyMovie = DataDummy.generateDummyMovie()[0]
    private val dummyMovieId = dummyMovie.id

    private val dummyTvShow = DataDummy.generateDummyTvShow()[0]
    private val dummyTvShowId = dummyTvShow.id

    private val typeMovie = DetailActivity.MOVIE
    private val typeTvShow = DetailActivity.TV_SHOW

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieInteractor: MovieInteractor

    @Mock
    private lateinit var detailMovieObserver: Observer<Resource<Movie>>

    @Mock
    private lateinit var detailTvShowObserver: Observer<Resource<TvShow>>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(movieInteractor)
    }


    @Test
    fun testGetMovieDetail() {
        runBlocking {
            val flow = flow {
                delay(10)
                emit(Resource.Success(dummyMovie))
            }
            val dummyDetailMovie = Resource.Success(dummyMovie)
            `when`(movieInteractor.getDetailMovies(dummyMovieId)).thenReturn(flow)
            val movieEntity = viewModel.getMovie(dummyMovieId).value as Resource<Movie>
            verify(movieInteractor).getDetailMovies(dummyMovieId)
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
    }

    @Test
    fun testGetTvShowDetail() {
        runBlocking {
            val detailTvShow = Resource.Success(dummyTvShow)
            val tvShow = flow {
                delay(10)
                emit(Resource.Success(dummyTvShow))
            }

            `when`(movieInteractor.getDetailTvShow(dummyTvShowId)).thenReturn(tvShow)
            val tvShowEntity = viewModel.getTvShow(dummyTvShowId).value as Resource<TvShow>
            verify(movieInteractor).getDetailTvShow(dummyTvShowId)
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
    }

    @Test
    fun testSetFavoriteMovie() {
        viewModel.setFavorite(dummyMovieId, true, typeMovie)
        verify(movieInteractor).setFavoriteMovie(dummyMovieId, true)
    }

    @Test
    fun testSetFavoriteTvShow() {
        viewModel.setFavorite(dummyTvShowId, true, typeTvShow)
        verify(movieInteractor).setFavoriteTvShow(dummyTvShowId, true)
    }
}