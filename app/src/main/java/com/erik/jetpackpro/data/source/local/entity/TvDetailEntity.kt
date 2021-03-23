package com.erik.jetpackpro.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.erik.jetpackpro.data.source.remote.response.tvdetail.*

@Entity(tableName = "tv_detail")
data class TvDetailEntity(
        @PrimaryKey
        @NonNull
        val id: Int,

        val name: String,
        val posterPath: String,
        val backdropPath: String,
        val voteAverage: Double,
        val voteCount: Int,
        val tagline: String,
        val homepage: String,
        val firstAirDate: String,
        val numberOfEpisode: Int,
        val numberOfSeasons: Int,
        val genres: List<GenresItem>,
        val productionCompany: List<ProductionCompaniesItem>,
        val overview: String,
        val status: String,
        val lastEpisode: LastEpisodeToAir,
        val nextEpisode: NextEpisodeToAir,
        var favorite: Boolean = false
){
    companion object{
        private fun tvDetailItem(tv: TvDetailResponse) : TvDetailEntity{
            return TvDetailEntity(
                    tv.id,
                    tv.name,
                    tv.posterPath,
                    tv.backdropPath,
                    tv.voteAverage,
                    tv.voteCount,
                    tv.tagline,
                    tv.homepage,
                    tv.firstAirDate,
                    tv.numberOfEpisodes,
                    tv.numberOfSeasons,
                    tv.genres,
                    tv.productionCompanies,
                    tv.overview,
                    tv.status,
                    tv.lastEpisodeToAir,
                    tv.nextEpisodeToAir?: NextEpisodeToAir("","","",0,0.0,"",0,0,"",0),
                    false

            )
        }

        fun fromTvDetailResponse(tv: TvDetailResponse): TvDetailEntity{
            return tvDetailItem(tv)
        }
    }
}