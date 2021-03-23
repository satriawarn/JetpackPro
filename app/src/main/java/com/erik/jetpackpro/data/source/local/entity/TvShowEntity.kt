package com.erik.jetpackpro.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.erik.jetpackpro.data.source.remote.response.tv.ResultsItemTv
import com.erik.jetpackpro.data.source.remote.response.tv.TvResponse
import com.erik.jetpackpro.data.source.remote.response.tvdetail.TvDetailResponse

@Entity(tableName = "tv")
data class TvShowEntity(
        @PrimaryKey
        @NonNull
        val id: Int,

        val name: String,
        val posterPath: String,
        val popularity: Double,
        var favorite: Boolean = false
) {
    companion object {
        private fun tvItem(tvShow: ResultsItemTv?): TvShowEntity {
            return TvShowEntity(
                    tvShow?.id ?: 0,
                    tvShow?.name ?: "",
                    tvShow?.posterPath ?: "",
                    tvShow?.popularity ?: 0.0,
                    false
            )
        }

        fun fromTVShowsResponse(tvShows: TvResponse): List<TvShowEntity> {
            return tvShows.results.map { tvItem(it) }
        }
    }
}