package com.augie.moviecatalogue.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.augie.moviecatalogue.databinding.FragmentTvShowBinding
import com.augie.moviecatalogue.ui.adapter.MovieAdapter
import com.augie.moviecatalogue.ui.detail.DetailMovieActivity
import com.augie.moviecatalogue.viewmodel.ViewModelFactory

class TvShowFragment : Fragment() {
    private lateinit var binding: FragmentTvShowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance()
            val viewModel = ViewModelProvider(
                this,
                factory
            )[TvShowViewModel::class.java]
            val adapter = MovieAdapter(DetailMovieActivity.TV_SHOW)

            loadingState(true)

            viewModel.getTvShows().observe(viewLifecycleOwner, { listTvShows ->
                adapter.setMovie(listTvShows)
                with(binding.rvTvShow) {
                    layoutManager = LinearLayoutManager(context)
                    this.adapter = adapter
                    setHasFixedSize(true)
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
}