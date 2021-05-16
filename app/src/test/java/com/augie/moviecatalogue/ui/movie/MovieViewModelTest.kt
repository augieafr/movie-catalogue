package com.augie.moviecatalogue.ui.movie

import com.augie.moviecatalogue.data.MovieEntity
import com.augie.moviecatalogue.utils.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel
    private val dataSize = DataDummy.generateDummyMovie().size

    @Before
    fun setUp() {
        viewModel = MovieViewModel()
    }

    @Test
    fun testGetMovie() {
        val movieEntities = viewModel.getMovie()
        assertNotNull(movieEntities)
        assertEquals(dataSize, movieEntities.size)
    }

}