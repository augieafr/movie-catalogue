package com.augie.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import com.augie.moviecatalogue.R
import com.augie.moviecatalogue.data.MovieEntity
import com.augie.moviecatalogue.databinding.ActivityDetailMovieBinding
import com.augie.moviecatalogue.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailMovieActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var movie: MovieEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loadingState(true)

        val id = intent.getIntExtra(EXTRA_ID, 0)
        val type = intent.getStringExtra(EXTRA_TYPE)

        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(
            this,
            factory
        )[DetailMovieViewModel::class.java]

        if (type != null) {
            viewModel.getMovie(type, id).observe(this, { detail ->
                populateView(detail)
                movie = detail
                loadingState(false)
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.civ_favorite -> {
                with(binding.civFavorite) {
                    setImageResource(R.drawable.ic_baseline_favorite_24)
                    tag = R.drawable.ic_baseline_favorite_24
                }
                Toast.makeText(
                    this,
                    "Will be fully implemented in submission 3",
                    Toast.LENGTH_SHORT
                ).show()
            }

            R.id.civ_share -> {
                val mimeType = "text/plain"
                ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Share this film now")
                    .setText(resources.getString(R.string.share_text, movie.title, movie.overview))
                    .startChooser()
            }
        }
    }

    private fun loadingState(loading: Boolean) {
        if (loading) {
            with(binding) {
                progressBar.visibility = View.VISIBLE
                constraintLayout.visibility = View.GONE
            }
        } else {
            with(binding) {
                progressBar.visibility = View.GONE
                constraintLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun populateView(movie: MovieEntity) {

        with(binding) {
            tvDetailTitle.text =
                getString(R.string.movie_detail_title, movie.title, movie.releaseDate)
            tvDetailGenre.text = movie.genre
            tvDetailOverview.text = movie.overview
            tvDetailDuration.text = movie.duration

            // tag for instrumental testing purpose
            imgDetailPoster.tag = movie.poster
            civFavorite.tag = R.drawable.ic_baseline_favorite_border_24

            civFavorite.setOnClickListener(this@DetailMovieActivity)
            civShare.setOnClickListener(this@DetailMovieActivity)
        }

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/original${movie.poster}")
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.imgDetailPoster)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/original${movie.backdrop}")
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(binding.imgDetailBackdrop)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TYPE = "extra_type"
        const val MOVIE = "movie"
        const val TV_SHOW = "tv_show"
    }
}