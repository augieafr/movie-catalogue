package com.augie.moviecatalogue.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.augie.moviecatalogue.core.R
import com.augie.moviecatalogue.core.databinding.MovieItemsBinding
import com.augie.moviecatalogue.core.domain.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var onItemClick: ((Movie) -> Unit)? = null
    private var listMovie = ArrayList<Movie>()

    // semenjak update android studio artic fox, terdapat warning performance 
    // saat inspect code mengenai notifyDataSetChanged(). 
    // tetapi saya tetap menggunakan notifyDataSetChanged() karena adapter ini
    // hanya menagani load semua data dari database, tidak menangani event add atau delete item tertentu.
    // ref: https://stackoverflow.com/questions/68602157/it-will-always-be-more-efficient-to-use-more-specific-change-events-if-you-can
    fun setData(data: List<Movie>?) {
        if (data == null) return
        listMovie.clear()
        listMovie.addAll(data)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(private val binding: MovieItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                tvMovieTitle.text = movie.title
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/original${movie.poster}")
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgMoviePoster)

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/original${movie.backdrop}")
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgMovieBackdrop)

                itemView.setOnClickListener {
                    onItemClick?.invoke(movie)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding = MovieItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.bind(movie)
    }

    override fun getItemCount() = listMovie.size


}