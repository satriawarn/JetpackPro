package com.erik.jetpackpro.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.erik.jetpackpro.data.source.remote.response.moviedetail.MovieDetailResponse
import com.erik.jetpackpro.data.source.remote.response.moviedetail.MovieGenres
import com.erik.jetpackpro.data.source.remote.response.moviedetail.ProductionCompany

@Entity(tableName = "movie_detail")
data class MovieDetailEntity(
        @PrimaryKey
        @NonNull
        val id: Int,

        val title: String,
        val overview: String,
        val popularity: Double,
        val posterPath: String,
        val backdropPath: String,
        val releaseDate: String,
        val voteAverage: Double,
        val voteCount: Int,
        val tagline: String,
        val homepage: String,
        val runtime: Int,
        val genres: List<MovieGenres>,
        val productionCompany: List<ProductionCompany>,
        var favorite: Boolean = false
) {
    companion object {
        private fun movieDetailItem(movies: MovieDetailResponse): MovieDetailEntity {
            return MovieDetailEntity(
                    movies.id ?: 0,
                    movies.title ?: "",
                    movies.overview ?: "",
                    movies.popularity ?: 0.0,
                    movies.posterPath ?: "",
                    movies.backdropPath ?: "",
                    movies.releaseDate ?: "",
                    movies.voteAverage ?: 0.0,
                    movies.voteCount ?: 0,
                    movies.tagline ?: "",
                    movies.homepage ?: "",
                    movies.runtime ?: 0,
                    movies.genres,
                    movies.productionCompanies,
                    false
            )
        }

        fun fromMovieDetailResponse(movies: MovieDetailResponse): MovieDetailEntity {
            return movieDetailItem(movies)
        }
    }
}