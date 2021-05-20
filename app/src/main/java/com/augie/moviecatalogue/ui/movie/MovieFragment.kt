package com.augie.moviecatalogue.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.augie.moviecatalogue.databinding.FragmentMovieBinding
import com.augie.moviecatalogue.ui.adapter.MovieAdapter
import com.augie.moviecatalogue.ui.detail.DetailMovieActivity
import com.augie.moviecatalogue.viewmodel.ViewModelFactory

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance()
            val viewModel = ViewModelProvider(
                this,
                factory
            )[MovieViewModel::class.java]

            val adapter = MovieAdapter(DetailMovieActivity.MOVIE)

            loadingState(true)
            viewModel.getMovie().observe(this, { listMovies ->
                    adapter.setMovie(listMovies)
                    with(binding.rvMovie) {
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