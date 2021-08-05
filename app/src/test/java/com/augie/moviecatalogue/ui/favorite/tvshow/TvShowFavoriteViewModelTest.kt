package com.augie.moviecatalogue.ui.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.augieafr.moviecatalogue.favorite.tvshow.TvShowFavoriteViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowFavoriteViewModelTest {

    private lateinit var viewModel: com.augieafr.moviecatalogue.favorite.tvshow.TvShowFavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: com.augie.moviecatalogue.core.data.MovieRepository

    @Mock
    private lateinit var tvShowObserver: Observer<PagedList<com.augie.moviecatalogue.core.data.source.local.entity.TvShowEntity>>

    @Mock
    private lateinit var dummyFavoriteTvShow: PagedList<com.augie.moviecatalogue.core.data.source.local.entity.TvShowEntity>

    @Before
    fun setUp() {
        viewModel =
            com.augieafr.moviecatalogue.favorite.tvshow.TvShowFavoriteViewModel(movieRepository)
    }

    @Test
    fun testGetTvShowFavorite() {
        `when`(dummyFavoriteTvShow.size).thenReturn(5)
        val tvShows = MutableLiveData<PagedList<com.augie.moviecatalogue.core.data.source.local.entity.TvShowEntity>>()
        tvShows.value = dummyFavoriteTvShow

        `when`(movieRepository.getFavoriteTvShows()).thenReturn(tvShows)
        val tvShowEntities = viewModel.getTvShowFavorite().value
        verify(movieRepository).getFavoriteTvShows()

        Assert.assertNotNull(tvShowEntities)
        Assert.assertEquals(5, tvShowEntities?.size)

        viewModel.getTvShowFavorite().observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyFavoriteTvShow)
    }
}