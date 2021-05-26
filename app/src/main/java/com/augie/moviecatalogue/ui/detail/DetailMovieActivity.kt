package com.augie.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.augie.moviecatalogue.R
import com.augie.moviecatalogue.data.source.local.entity.MovieEntity
import com.augie.moviecatalogue.data.source.local.entity.TvShowEntity
import com.augie.moviecatalogue.databinding.ActivityDetailMovieBinding
import com.augie.moviecatalogue.viewmodel.ViewModelFactory
import com.augie.moviecatalogue.vo.Resource
import com.augie.moviecatalogue.vo.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailMovieActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var viewModel: DetailMovieViewModel
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

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(
            this,
            factory
        )[DetailMovieViewModel::class.java]

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
                if (!isFav){
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

    private val movieObserver = Observer<Resource<MovieEntity>> { detailMovie ->
        if (detailMovie != null) {
            when (detailMovie.status) {
                Status.LOADING -> loadingState(true)
                Status.ERROR -> {

                }
                Status.SUCCESS -> {
                    if (detailMovie.data != null) {
                        populateView(detailMovie.data)
                        movieTitle = detailMovie.data.title
                        movieOverview = detailMovie.data.overview
                        isFav = detailMovie.data.isFav
                        id = detailMovie.data.id
                    }
                    loadingState(false)
                }
            }
        }
    }

    private val tvShowObserver = Observer<Resource<TvShowEntity>> { detailTvShow ->
        if (detailTvShow != null) {
            when (detailTvShow.status) {
                Status.LOADING -> loadingState(true)
                Status.ERROR -> {

                }
                Status.SUCCESS -> {
                    if (detailTvShow.data != null) {
                        populateView(detailTvShow.data)
                        movieTitle = detailTvShow.data.title
                        movieOverview = detailTvShow.data.overview
                        isFav = detailTvShow.data.isFav
                        id = detailTvShow.data.id
                    }
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

    private fun populateView(movie: MovieEntity) {

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

    private fun populateView(tvShow: TvShowEntity) {

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

            civFavorite.setOnClickListener(this@DetailMovieActivity)
            civShare.setOnClickListener(this@DetailMovieActivity)
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

    private fun favoriteState(state: Boolean){
        if (state){
            with(binding.civFavorite) {
                setImageResource(R.drawable.ic_baseline_favorite_24)
                tag = R.drawable.ic_baseline_favorite_24
            }
            Toast.makeText(
                this,
                "Added to favorite",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            with(binding.civFavorite) {
                setImageResource(R.drawable.ic_baseline_favorite_border_24)
                tag = R.drawable.ic_baseline_favorite_border_24
            }
            Toast.makeText(
                this,
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