package com.augie.moviecatalogue.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.augie.moviecatalogue.core.R
import com.augie.moviecatalogue.core.databinding.MovieItemsBinding
import com.augie.moviecatalogue.core.domain.model.TvShow
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {
    var onItemClick: ((TvShow) -> Unit)? = null
    private var listTvShow = ArrayList<TvShow>()

    // semenjak update android studio artic fox, terdapat warning performance
    // saat inspect code mengenai notifyDataSetChanged().
    // tetapi saya tetap menggunakan notifyDataSetChanged() karena adapter ini
    // hanya menagani load semua data dari database, tidak menangani event add atau delete item tertentu.
    // ref: https://stackoverflow.com/questions/68602157/it-will-always-be-more-efficient-to-use-more-specific-change-events-if-you-can
    fun setData(data: List<TvShow>?) {
        if (data == null) return
        listTvShow.clear()
        listTvShow.addAll(data)
        notifyDataSetChanged()
    }

    inner class TvShowViewHolder(private val binding: MovieItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShow) {
            with(binding) {
                tvMovieTitle.text = tvShow.title
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/original${tvShow.poster}")
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgMoviePoster)

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/original${tvShow.backdrop}")
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgMovieBackdrop)


                itemView.setOnClickListener {
                    onItemClick?.invoke(tvShow)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemsAcademyBinding =
            MovieItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemsAcademyBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = listTvShow[position]
        holder.bind(tvShow)
    }

    override fun getItemCount(): Int = listTvShow.size
}