package com.augie.moviecatalogue.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.augie.moviecatalogue.databinding.ActivityFavoriteBinding
import com.augie.moviecatalogue.ui.adapter.SectionsPagerAdapter
import com.augie.moviecatalogue.utils.Type
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFavoriteBinding.inflate(layoutInflater)

        setContentView(binding.root)
        supportActionBar?.hide()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, Type.FAVORITE)
        binding.favoriteViewPager.adapter = sectionsPagerAdapter

        val tabTitles = arrayOf("Movies", "TV Shows")

        TabLayoutMediator(binding.favoriteTabLayout, binding.favoriteViewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}