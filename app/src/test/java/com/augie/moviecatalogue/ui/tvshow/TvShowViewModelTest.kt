package com.augie.moviecatalogue.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.augie.moviecatalogue.data.source.local.entity.MovieEntity
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
class TvShowViewModelTest {
    private lateinit var viewModel: TvShowViewModel
    private val dataSize = DataDummy.generateDummyTvShow().size

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var tvShowObserver: Observer<List<MovieEntity>>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(movieRepository)
    }

    @Test
    fun testGetTvShow() {
        val dummyTvShows = DataDummy.generateDummyTvShow()
        val tvShows = MutableLiveData<List<MovieEntity>>()
        tvShows.value = dummyTvShows

        `when`(movieRepository.getTvShows()).thenReturn(tvShows)
        val tvShowEntities = viewModel.getTvShows().value as List<MovieEntity>
        verify(movieRepository).getTvShows()

        assertNotNull(tvShowEntities)
        assertEquals(dataSize, tvShowEntities.size)

        viewModel.getTvShows().observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTvShows)
    }

}