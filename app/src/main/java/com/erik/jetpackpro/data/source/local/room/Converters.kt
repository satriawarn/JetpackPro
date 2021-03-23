package com.erik.jetpackpro.data.source.local.room

import androidx.room.TypeConverter
import com.erik.jetpackpro.data.source.remote.response.moviedetail.MovieGenres
import com.erik.jetpackpro.data.source.remote.response.moviedetail.ProductionCompany
import com.erik.jetpackpro.data.source.remote.response.tvdetail.GenresItem
import com.erik.jetpackpro.data.source.remote.response.tvdetail.LastEpisodeToAir
import com.erik.jetpackpro.data.source.remote.response.tvdetail.NextEpisodeToAir
import com.erik.jetpackpro.data.source.remote.response.tvdetail.ProductionCompaniesItem
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun listToJsonGenres(value: List<MovieGenres>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListGenres(value: String) = Gson().fromJson(value, Array<MovieGenres>::class.java).toList()

    @TypeConverter
    fun listToJsonProductions(value: List<ProductionCompany>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListGProductions(value: String) = Gson().fromJson(value, Array<ProductionCompany>::class.java).toList()

    @TypeConverter
    fun listToJsonGenresTv(value: List<GenresItem>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListGenresTv(value: String) = Gson().fromJson(value, Array<GenresItem>::class.java).toList()

    @TypeConverter
    fun listToJsonProductionsTv(value: List<ProductionCompaniesItem>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToListProductionsTv(value: String) = Gson().fromJson(value, Array<ProductionCompaniesItem>::class.java).toList()

    @TypeConverter
    fun gsonToLastEpisode(value: LastEpisodeToAir) = Gson().toJson(value)

    @TypeConverter
    fun gsonToJsonLastEpisode(value: String) = Gson().fromJson(value, LastEpisodeToAir::class.java)

    @TypeConverter
    fun gsonToNextEpisode(value: NextEpisodeToAir) = Gson().toJson(value)

    @TypeConverter
    fun gsonToJsonNextEpisode(value: String) = Gson().fromJson(value, NextEpisodeToAir::class.java)
}