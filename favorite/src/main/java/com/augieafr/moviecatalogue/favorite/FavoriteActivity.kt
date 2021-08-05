package com.augieafr.moviecatalogue.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.augie.moviecatalogue.core.ui.SectionsPagerAdapter
import com.augieafr.moviecatalogue.favorite.databinding.ActivityFavoriteBinding
import com.augieafr.moviecatalogue.favorite.di.mapsModule
import com.augieafr.moviecatalogue.favorite.movie.MovieFavoriteFragment
import com.augieafr.moviecatalogue.favorite.tvshow.TvShowFavoriteFragment
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFavoriteBinding.inflate(layoutInflater)

        setContentView(binding.root)
        supportActionBar?.title = "My Favorite"

        loadKoinModules(mapsModule)

        val sectionsPagerAdapter =
            SectionsPagerAdapter(this, MovieFavoriteFragment(), TvShowFavoriteFragment())
        binding.favoriteViewPager.adapter = sectionsPagerAdapter

        val tabTitles = arrayOf("Movies", "TV Shows")

        TabLayoutMediator(binding.favoriteTabLayout, binding.favoriteViewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}