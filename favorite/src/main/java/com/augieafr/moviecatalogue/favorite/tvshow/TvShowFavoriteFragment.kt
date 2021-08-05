package com.augieafr.moviecatalogue.favorite.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.augie.moviecatalogue.core.ui.TvShowAdapter
import com.augie.moviecatalogue.detail.DetailActivity
import com.augieafr.moviecatalogue.favorite.databinding.FragmentTvShowFavoriteBinding
import org.koin.android.viewmodel.ext.android.viewModel


class TvShowFavoriteFragment : Fragment() {

    private lateinit var binding: FragmentTvShowFavoriteBinding
    private val viewModel: TvShowFavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val adapter = TvShowAdapter()

            loadingState(true)
            viewModel.getTvShowFavorite().observe(this, { listTvShow ->
                if (listTvShow.isEmpty()) {
                    dataState(true)
                } else {
                    dataState(false)
                    adapter.setData(listTvShow)
                    adapter.onItemClick = {
                        val intent = Intent(activity, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_ID, it.id)
                        intent.putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.TV_SHOW)
                        startActivity(intent)
                    }
                    with(binding.rvFavoriteTvShow) {
                        layoutManager = LinearLayoutManager(context)
                        this.adapter = adapter
                        setHasFixedSize(true)
                    }
                }
                loadingState(false)
            })
        }
    }

    private fun loadingState(loading: Boolean) {
        if (loading) {
            with(binding) {
                progressBar.visibility = View.VISIBLE
            }
        } else {
            with(binding) {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun dataState(empty: Boolean) {
        if (empty) {
            with(binding) {
                tvNoTvShowFavorite.visibility = View.VISIBLE
                rvFavoriteTvShow.visibility = View.GONE
            }
        } else {
            with(binding) {
                tvNoTvShowFavorite.visibility = View.GONE
                rvFavoriteTvShow.visibility = View.VISIBLE
            }
        }
    }
}