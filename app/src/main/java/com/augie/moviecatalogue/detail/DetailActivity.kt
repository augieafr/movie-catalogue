package com.augie.moviecatalogue.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import com.augie.moviecatalogue.R
import com.augie.moviecatalogue.core.data.Resource
import com.augie.moviecatalogue.core.domain.model.Movie
import com.augie.moviecatalogue.core.domain.model.TvShow
import com.augie.moviecatalogue.databinding.ActivityDetailMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailMovieBinding
    private val viewModel: DetailViewModel by viewModel()
    private lateinit var movieTitle: String
    private lateinit var movieOverview: String
    private lateinit var type: String

    private var id: Int = 0
    private var isFav: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loadingState(true)

        val id = intent.getIntExtra(EXTRA_ID, 0)
        type = intent.getStringExtra(EXTRA_TYPE).toString()

        when (type) {
            MOVIE -> {
                viewModel.getMovie(id).observe(this, movieObserver)
            }

            TV_SHOW -> {
                viewModel.getTvShow(id).observe(this, tvShowObserver)
            }
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
                if (!isFav) {
                    // update to favorite
                    viewModel.setFavorite(id, true, type)
                    favoriteState(true)
                } else {
                    // update to not favorite
                    viewModel.setFavorite(id, false, type)
                    favoriteState(false)
                }
            }

            R.id.civ_share -> {
                val mimeType = "text/plain"
                ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Share this film now")
                    .setText(resources.getString(R.string.share_text, movieTitle, movieOverview))
                    .startChooser()
            }
        }
    }

    private val movieObserver = Observer<Resource<Movie>> { detailMovie ->
        if (detailMovie != null) {
            when (detailMovie) {
                is Resource.Loading -> loadingState(true)
                is Resource.Error -> {

                }
                is Resource.Success -> {
                    populateView(detailMovie.data)
                    loadingState(false)
                }
            }
        }
    }

    private val tvShowObserver = Observer<Resource<TvShow>> { detailTvShow ->
        if (detailTvShow != null) {
            when (detailTvShow) {
                is Resource.Loading -> loadingState(true)
                is Resource.Error -> {

                }
                is Resource.Success -> {
                    populateView(detailTvShow.data)
                    loadingState(false)
                }
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

    private fun populateView(movie: Movie?) {

        if (movie != null) {
            movieTitle = movie.title
            movieOverview = movie.overview
            isFav = movie.isFav
            id = movie.id

            with(binding) {
                tvDetailTitle.text =
                    getString(R.string.movie_detail_title, movie.title, movie.releaseDate)
                tvDetailGenre.text = movie.genre
                tvDetailOverview.text = movie.overview
                tvDetailDuration.text = movie.duration

                // tag for instrumental testing purpose
                imgDetailPoster.tag = movie.poster
                if (!movie.isFav) {
                    civFavorite.tag = R.drawable.ic_baseline_favorite_border_24
                } else {
                    civFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                    civFavorite.tag = R.drawable.ic_baseline_favorite_24
                }

                civFavorite.setOnClickListener(this@DetailActivity)
                civShare.setOnClickListener(this@DetailActivity)
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

    }

    private fun populateView(tvShow: TvShow?) {
        if (tvShow != null) {
            movieTitle = tvShow.title
            movieOverview = tvShow.overview
            isFav = tvShow.isFav
            id = tvShow.id

            with(binding) {
                tvDetailTitle.text =
                    getString(R.string.movie_detail_title, tvShow.title, tvShow.releaseDate)
                tvDetailGenre.text = tvShow.genre
                tvDetailOverview.text = tvShow.overview
                tvDetailDuration.text = tvShow.duration

                // tag for instrumental testing purpose
                imgDetailPoster.tag = tvShow.poster
                if (!tvShow.isFav) {
                    civFavorite.tag = R.drawable.ic_baseline_favorite_border_24
                } else {
                    civFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                    civFavorite.tag = R.drawable.ic_baseline_favorite_24
                }

                civFavorite.setOnClickListener(this@DetailActivity)
                civShare.setOnClickListener(this@DetailActivity)
            }

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/original${tvShow.poster}")
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(binding.imgDetailPoster)

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/original${tvShow.backdrop}")
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(binding.imgDetailBackdrop)
        }

    }

    private fun favoriteState(state: Boolean) {
        if (state) {
            with(binding.civFavorite) {
                setImageResource(R.drawable.ic_baseline_favorite_24)
                tag = R.drawable.ic_baseline_favorite_24
            }
            Toast.makeText(
                applicationContext,
                "Added to favorite",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            with(binding.civFavorite) {
                setImageResource(R.drawable.ic_baseline_favorite_border_24)
                tag = R.drawable.ic_baseline_favorite_border_24
            }
            Toast.makeText(
                applicationContext,
                "Removed from favorite",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TYPE = "extra_type"
        const val MOVIE = "movie"
        const val TV_SHOW = "tv_show"
    }
}