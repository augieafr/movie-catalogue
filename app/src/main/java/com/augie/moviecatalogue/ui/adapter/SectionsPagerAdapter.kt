package com.augie.moviecatalogue.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.augie.moviecatalogue.ui.favorite.movie.MovieFavoriteFragment
import com.augie.moviecatalogue.ui.favorite.tvshow.TvShowFavoriteFragment
import com.augie.moviecatalogue.ui.home.movie.MovieFragment
import com.augie.moviecatalogue.ui.home.tvshow.TvShowFragment
import com.augie.moviecatalogue.utils.Type

class SectionsPagerAdapter(activity: AppCompatActivity, private val type: Type) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = if (type == Type.HOME) MovieFragment() else MovieFavoriteFragment()
            1 -> fragment = if (type == Type.HOME) TvShowFragment() else TvShowFavoriteFragment()
        }
        return fragment as Fragment
    }

}