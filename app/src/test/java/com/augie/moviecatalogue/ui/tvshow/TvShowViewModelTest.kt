package com.augie.moviecatalogue.ui.tvshow

import com.augie.moviecatalogue.utils.DataDummy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class TvShowViewModelTest {
    private lateinit var viewModel: TvShowViewModel
    private val dataSize = DataDummy.generateDummyTvShow().size

    @Before
    fun setUp() {
        viewModel = TvShowViewModel()
    }

    @Test
    fun testGetTvShow() {
        val tvShowEntities = viewModel.getTvShow()
        assertNotNull(tvShowEntities)
        assertEquals(dataSize, tvShowEntities.size)
    }

}