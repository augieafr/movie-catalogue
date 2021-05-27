package com.augie.moviecatalogue.ui.home.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.augie.moviecatalogue.databinding.FragmentMovieBinding
import com.augie.moviecatalogue.ui.adapter.MovieAdapter
import com.augie.moviecatalogue.viewmodel.ViewModelFactory
import com.augie.moviecatalogue.vo.Status

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
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(
                this,
                factory
            )[MovieViewModel::class.java]

            val adapter = MovieAdapter()


            viewModel.getMovie().observe(this, { listMovie ->
                if (listMovie != null) {
                    when (listMovie.status) {
                        Status.LOADING -> loadingState(true)
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                        Status.SUCCESS -> {
                            adapter.submitList(listMovie.data)
                            with(binding.rvMovie) {
                                layoutManager = LinearLayoutManager(context)
                                this.adapter = adapter
                                setHasFixedSize(true)
                            }

                            loadingState(false)
                        }
                    }
                }
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