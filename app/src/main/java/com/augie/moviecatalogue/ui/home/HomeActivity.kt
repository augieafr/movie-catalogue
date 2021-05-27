package com.augie.moviecatalogue.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.augie.moviecatalogue.R
import com.augie.moviecatalogue.databinding.ActivityHomeBinding
import com.augie.moviecatalogue.ui.adapter.SectionsPagerAdapter
import com.augie.moviecatalogue.ui.favorite.FavoriteActivity
import com.augie.moviecatalogue.utils.Type
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, Type.HOME)
        binding.mainViewPager.adapter = sectionsPagerAdapter


        val tabTitles = arrayOf("Movies", "TV Shows")

        TabLayoutMediator(binding.mainTabLayout, binding.mainViewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                // intent to favorite activity
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}