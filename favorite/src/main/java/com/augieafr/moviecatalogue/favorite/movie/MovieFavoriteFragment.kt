package com.augieafr.moviecatalogue.favorite.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.augie.moviecatalogue.core.ui.MovieAdapter
import com.augie.moviecatalogue.detail.DetailActivity
import com.augieafr.moviecatalogue.favorite.databinding.FragmentMovieFavoriteBinding
import org.koin.android.viewmodel.ext.android.viewModel


class MovieFavoriteFragment : Fragment() {

    private lateinit var binding: FragmentMovieFavoriteBinding
    private val viewModel: MovieFavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val adapter = MovieAdapter()

            loadingState(true)
            viewModel.getMovieFavorite().observe(this, { listMovie ->
                if (listMovie.isEmpty()) {
                    dataState(true)
                } else {
                    dataState(false)
                    adapter.setData(listMovie)
                    adapter.onItemClick = {
                        val intent = Intent(activity, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_ID, it.id)
                        intent.putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.MOVIE)
                        startActivity(intent)
                    }
                    with(binding.rvFavoriteMovie) {
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
                tvNoMovieFavorite.visibility = View.VISIBLE
                rvFavoriteMovie.visibility = View.GONE
            }
        } else {
            with(binding) {
                tvNoMovieFavorite.visibility = View.GONE
                rvFavoriteMovie.visibility = View.VISIBLE
            }
        }
    }
}