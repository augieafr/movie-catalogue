package com.augie.moviecatalogue.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.augie.moviecatalogue.databinding.FragmentTvShowBinding
import com.augie.moviecatalogue.ui.adapter.TvShowAdapter
import com.augie.moviecatalogue.viewmodel.ViewModelFactory
import com.augie.moviecatalogue.vo.Status

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
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(
                this,
                factory
            )[TvShowViewModel::class.java]

            val adapter = TvShowAdapter()


            viewModel.getTvShows().observe(viewLifecycleOwner, { listTvShows ->
                if (listTvShows != null) {
                    when (listTvShows.status) {
                        Status.LOADING -> loadingState(true)
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                        Status.SUCCESS -> {
                            adapter.submitList(listTvShows.data)
                            with(binding.rvTvShow) {
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