package com.augie.moviecatalogue.home.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.augie.moviecatalogue.core.data.Resource
import com.augie.moviecatalogue.core.ui.TvShowAdapter
import com.augie.moviecatalogue.databinding.FragmentTvShowBinding
import com.augie.moviecatalogue.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {
    private lateinit var binding: FragmentTvShowBinding
    private val viewModel: TvShowViewModel by viewModel()

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


            val adapter = TvShowAdapter()

            viewModel.getTvShows().observe(viewLifecycleOwner, { listTvShows ->
                if (listTvShows != null) {
                    when (listTvShows) {
                        is Resource.Loading -> loadingState(true)
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Success -> {
                            adapter.setData(listTvShows.data)
                            adapter.onItemClick = {
                                val intent = Intent(activity, DetailActivity::class.java)
                                intent.putExtra(DetailActivity.EXTRA_ID, it.id)
                                intent.putExtra(
                                    DetailActivity.EXTRA_TYPE,
                                    DetailActivity.TV_SHOW
                                )
                                startActivity(intent)
                            }
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