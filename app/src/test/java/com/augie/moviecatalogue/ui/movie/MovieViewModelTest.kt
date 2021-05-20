package com.augie.moviecatalogue.ui.movie

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
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel
    private val dataSize = DataDummy.generateDummyMovie().size

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var  movieObserver: Observer<List<MovieEntity>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(movieRepository)
    }

    @Test
    fun testGetMovie() {
        val dummyMovies = DataDummy.generateDummyMovie()
        val movies = MutableLiveData<List<MovieEntity>>()
        movies.value = dummyMovies

        `when`(movieRepository.getMovies()).thenReturn(movies)
        val movieEntities = viewModel.getMovie().value as List<MovieEntity>
        verify(movieRepository).getMovies()

        assertNotNull(movieEntities)
        assertEquals(dataSize, movieEntities.size)

        viewModel.getMovie().observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovies)
    }

}