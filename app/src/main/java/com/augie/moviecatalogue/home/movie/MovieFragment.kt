package com.augie.moviecatalogue.home.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.augie.moviecatalogue.core.data.Resource
import com.augie.moviecatalogue.core.ui.MovieAdapter
import com.augie.moviecatalogue.databinding.FragmentMovieBinding
import com.augie.moviecatalogue.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel: MovieViewModel by viewModel()

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
            val adapter = MovieAdapter()


            viewModel.movie.observe(this, { listMovie ->
                if (listMovie != null) {
                    when (listMovie) {
                        is Resource.Loading -> loadingState(true)

                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }

                        is Resource.Success -> {
                            adapter.setData(listMovie.data)
                            adapter.onItemClick = {
                                val intent = Intent(activity, DetailActivity::class.java)
                                intent.putExtra(DetailActivity.EXTRA_ID, it.id)
                                intent.putExtra(
                                    DetailActivity.EXTRA_TYPE,
                                    DetailActivity.MOVIE
                                )
                                startActivity(intent)
                            }
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