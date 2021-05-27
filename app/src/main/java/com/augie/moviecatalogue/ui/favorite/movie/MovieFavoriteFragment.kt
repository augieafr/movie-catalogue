package com.augie.moviecatalogue.ui.favorite.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.augie.moviecatalogue.databinding.FragmentMovieFavoriteBinding
import com.augie.moviecatalogue.ui.adapter.MovieAdapter
import com.augie.moviecatalogue.viewmodel.ViewModelFactory


class MovieFavoriteFragment : Fragment() {

    private lateinit var binding: FragmentMovieFavoriteBinding

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
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(
                this,
                factory
            )[MovieFavoriteViewModel::class.java]

            val adapter = MovieAdapter()

            loadingState(true)
            viewModel.getMovieFavorite().observe(this, { listMovie ->
                if (listMovie.isEmpty()) {
                    dataState(true)
                } else {
                    dataState(false)
                    adapter.submitList(listMovie)
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