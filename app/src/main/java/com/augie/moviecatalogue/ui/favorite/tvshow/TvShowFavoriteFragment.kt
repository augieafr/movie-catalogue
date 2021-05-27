package com.augie.moviecatalogue.ui.favorite.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.augie.moviecatalogue.databinding.FragmentTvShowFavoriteBinding
import com.augie.moviecatalogue.ui.adapter.TvShowAdapter
import com.augie.moviecatalogue.viewmodel.ViewModelFactory


class TvShowFavoriteFragment : Fragment() {

    private lateinit var binding: FragmentTvShowFavoriteBinding

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
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(
                this,
                factory
            )[TvShowFavoriteViewModel::class.java]

            val adapter = TvShowAdapter()

            loadingState(true)
            viewModel.getTvShowFavorite().observe(this, { listTvShow ->
                if (listTvShow.isEmpty()) {
                    dataState(true)
                } else {
                    dataState(false)
                    adapter.submitList(listTvShow)
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