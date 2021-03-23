package com.erik.jetpackpro.data.source.remote

import androidx.lifecycle.LiveData
import com.erik.jetpackpro.data.source.remote.response.ApiResponse
import com.erik.jetpackpro.data.source.remote.response.movie.MovieResponse
import com.erik.jetpackpro.data.source.remote.response.moviedetail.MovieDetailResponse

interface MovieDataSourceInterface {
    fun getDiscoverMovie(): LiveData<ApiResponse<MovieResponse>>
    fun getMovieDetail(id: Int): LiveData<ApiResponse<MovieDetailResponse>>
}