package com.augie.moviecatalogue.ui.detail

import com.augie.moviecatalogue.utils.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class DetailMovieViewModelTest {
    private lateinit var viewModel: DetailMovieViewModel
    private val dummyMovie = DataDummy.generateDummyMovie()[0]
    private val dummyTvShow = DataDummy.generateDummyTvShow()[0]
    private val typeMovie = DetailMovieActivity.MOVIE
    private val typeTvShow = DetailMovieActivity.TV_SHOW

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel()
        // the first item or item with id 0
        viewModel.setSelectedMovie(0)
    }

    @Test
    fun testGetMovieDetail() {
        val movieEntity = viewModel.getMovie(typeMovie)
        assertNotNull(movieEntity)
        assertEquals(dummyMovie.title, movieEntity.title)
        assertEquals(dummyMovie.overview, movieEntity.overview)
        assertEquals(dummyMovie.releaseDate, movieEntity.releaseDate)
        assertEquals(dummyMovie.genre, movieEntity.genre)
        assertEquals(dummyMovie.duration, movieEntity.duration)
        assertEquals(dummyMovie.id, movieEntity.id)
        assertEquals(dummyMovie.poster, movieEntity.poster)
    }

    @Test
    fun testGetTvShowDetail() {
        val tvShowEntity = viewModel.getMovie(typeTvShow)
        assertNotNull(tvShowEntity)
        assertEquals(dummyTvShow.title, tvShowEntity.title)
        assertEquals(dummyTvShow.overview, tvShowEntity.overview)
        assertEquals(dummyTvShow.releaseDate, tvShowEntity.releaseDate)
        assertEquals(dummyTvShow.genre, tvShowEntity.genre)
        assertEquals(dummyTvShow.duration, tvShowEntity.duration)
        assertEquals(dummyTvShow.id, tvShowEntity.id)
        assertEquals(dummyTvShow.poster, tvShowEntity.poster)
    }
}