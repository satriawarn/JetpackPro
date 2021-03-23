package com.erik.jetpackpro.data.network

import com.erik.jetpackpro.data.source.remote.response.moviedetail.MovieDetailResponse
import com.erik.jetpackpro.data.source.remote.response.movie.MovieResponse
import com.erik.jetpackpro.data.source.remote.response.tv.TvResponse
import com.erik.jetpackpro.data.source.remote.response.tvdetail.TvDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndPoint {
    @GET("discover/movie")
    fun getDiscoverMovie(
            @Query("api_key") apikey: String,
            @Query("language") language: String,
            @Query("sort_by") sort_by: String
    ): Call<MovieResponse>

    @GET("discover/tv")
    fun getTvDiscover(
            @Query("api_key") apikey: String,
            @Query("language") language: String,
            @Query("page") page: String,
            @Query("sort_by") sort_by: String
    ): Call<TvResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
            @Path("movie_id") movie_id: Int,
            @Query("api_key") apikey: String,
            @Query("append_to_response") append_to_response: String
    ): Call<MovieDetailResponse>

    @GET("tv/{tv_id}")
    fun getTvDetail(
            @Path("tv_id") tv_id: Int,
            @Query("api_key") apikey: String,
            @Query("append_to_response") append_to_response: String
    ): Call<TvDetailResponse>
}