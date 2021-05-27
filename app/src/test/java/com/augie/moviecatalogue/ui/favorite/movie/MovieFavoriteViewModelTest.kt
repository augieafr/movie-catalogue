package com.augie.moviecatalogue.ui.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.augie.moviecatalogue.data.MovieRepository
import com.augie.moviecatalogue.data.source.local.entity.MovieEntity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieFavoriteViewModelTest {

    private lateinit var viewModel: MovieFavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var movieObserver: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var dummyFavoriteMovie: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = MovieFavoriteViewModel(movieRepository)
    }

    @Test
    fun testGetMovieFavorite() {
        Mockito.`when`(dummyFavoriteMovie.size).thenReturn(5)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyFavoriteMovie

        Mockito.`when`(movieRepository.getFavoriteMovies()).thenReturn(movies)
        val movieEntities = viewModel.getMovieFavorite().value
        Mockito.verify(movieRepository).getFavoriteMovies()

        Assert.assertNotNull(movieEntities)
        Assert.assertEquals(5, movieEntities?.size)

        viewModel.getMovieFavorite().observeForever(movieObserver)
        Mockito.verify(movieObserver).onChanged(dummyFavoriteMovie)
    }
}