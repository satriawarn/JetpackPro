package com.erik.jetpackpro.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.erik.jetpackpro.data.source.remote.response.movie.MovieResponse
import com.erik.jetpackpro.data.source.remote.response.movie.ResultsItemMovie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    val id: Int,

    val title: String,
    val popularity: Double,
    val posterPath: String,
    var favorite: Boolean = false
) {
    companion object {
        private fun movieItem(movie: ResultsItemMovie?): MovieEntity {
            return MovieEntity(
                movie?.id ?: 0,
                movie?.title ?: "",
                movie?.popularity ?: 0.0,
                movie?.posterPath ?: "",
                false
            )
        }

        fun fromMoviesResponse(movies: MovieResponse): List<MovieEntity> {
            return movies.results.map {
                movieItem(
                    it
                )
            }
        }
    }
}