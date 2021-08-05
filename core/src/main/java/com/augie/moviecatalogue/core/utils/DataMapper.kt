package com.augie.moviecatalogue.core.utils

import com.augie.moviecatalogue.core.data.source.local.entity.MovieEntity
import com.augie.moviecatalogue.core.data.source.local.entity.TvShowEntity
import com.augie.moviecatalogue.core.data.source.remote.response.DetailMovieResponse
import com.augie.moviecatalogue.core.data.source.remote.response.DetailTvShowResponse
import com.augie.moviecatalogue.core.data.source.remote.response.MovieItem
import com.augie.moviecatalogue.core.data.source.remote.response.TvItem
import com.augie.moviecatalogue.core.domain.model.Movie
import com.augie.moviecatalogue.core.domain.model.TvShow

object DataMapper {
    fun mapMovieResponsesToEntities(input: List<MovieItem>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map { item ->
            val movie = MovieEntity(
                id = item.id,
                title = item.title,
                overview = item.overview,
                // take only the year
                releaseDate = item.releaseDate?.take(4) ?: "No Info",
                genre = "",
                duration = "",
                poster = item.posterPath ?: "",
                backdrop = item.backdropPath ?: ""
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapTvShowResponsesToEntities(input: List<TvItem>): List<TvShowEntity> {
        val tvShowListList = ArrayList<TvShowEntity>()
        input.map { item ->
            val tvShow = TvShowEntity(
                id = item.id,
                title = item.originalName,
                overview = item.overview,
                // take only the year
                releaseDate = item.firstAirDate?.take(4) ?: "No Info",
                genre = "",
                duration = "",
                poster = item.posterPath ?: "",
                backdrop = item.backdropPath ?: ""
            )
            tvShowListList.add(tvShow)
        }
        return tvShowListList
    }

    fun mapDetailMovieResponseToEntity(input: DetailMovieResponse): MovieEntity {
        val listGenre = ArrayList<String>()
        for (genre in input.genres) {
            listGenre.add(genre.name)
        }

        // convert runtime to hour minute format
        val duration = if (input.runtime > 60) {
            val hour = input.runtime / 60
            val minute = input.runtime % 60
            "${hour}h ${minute}m"
        } else "${input.runtime}m"

        return MovieEntity(
            id = input.id,
            title = input.title,
            overview = input.overview,
            // take only the year
            releaseDate = input.releaseDate?.take(4) ?: "No Info",
            genre = listGenre.joinToString(", "),
            duration = duration,
            poster = input.posterPath ?: "",
            backdrop = input.backdropPath ?: ""
        )
    }

    fun mapDetailTvShowsResponseToEntity(input: DetailTvShowResponse): TvShowEntity {
        val listGenre = ArrayList<String>()
        for (genre in input.genres) {
            listGenre.add(genre.name)
        }

        // convert runtime to hour minute format
        val duration = if (input.episodeRunTime.isNotEmpty()) {
            if (input.episodeRunTime[0] > 60) {
                val hour = input.episodeRunTime[0] / 60
                val minute = input.episodeRunTime[0] % 60
                "${hour}h ${minute}m"
            } else {
                "${input.episodeRunTime[0]}m"
            }
        } else {
            ""
        }

        return TvShowEntity(
            id = input.id,
            title = input.originalName,
            overview = input.overview,
            releaseDate = input.firstAirDate?.take(4) ?: "No Info",
            genre = listGenre.joinToString(", "),
            duration = duration,
            poster = input.posterPath ?: "",
            backdrop = input.backdropPath ?: ""
        )
    }

    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<Movie> {
        val listMovie = ArrayList<Movie>()
        input.map { item ->
            val movie = Movie(
                id = item.id,
                title = item.title,
                overview = item.overview,
                releaseDate = item.releaseDate,
                genre = item.genre,
                duration = item.duration,
                poster = item.poster,
                backdrop = item.backdrop,
                isFav = item.isFav
            )
            listMovie.add(movie)
        }
        return listMovie
    }

    fun mapTvShowEntitiesToDomain(input: List<TvShowEntity>): List<TvShow> {
        val listTvShow = ArrayList<TvShow>()
        input.map { item ->
            val tvShow = TvShow(
                id = item.id,
                title = item.title,
                overview = item.overview,
                releaseDate = item.releaseDate,
                genre = item.genre,
                duration = item.duration,
                poster = item.poster,
                backdrop = item.backdrop,
                isFav = item.isFav
            )
            listTvShow.add(tvShow)
        }
        return listTvShow
    }

    fun mapMovieEntityToDomain(input: MovieEntity): Movie {
        return Movie(
            id = input.id,
            title = input.title,
            overview = input.overview,
            releaseDate = input.releaseDate,
            genre = input.genre,
            duration = input.duration,
            poster = input.poster,
            backdrop = input.backdrop,
            isFav = input.isFav
        )
    }

    fun mapDetailTvShowsEntityToDomain(input: TvShowEntity): TvShow {
        return TvShow(
            id = input.id,
            title = input.title,
            overview = input.overview,
            releaseDate = input.releaseDate,
            genre = input.genre,
            duration = input.duration,
            poster = input.poster,
            backdrop = input.backdrop,
            isFav = input.isFav
        )
    }


}