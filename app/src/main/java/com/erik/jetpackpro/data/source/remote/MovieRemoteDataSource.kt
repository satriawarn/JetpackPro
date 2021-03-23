package com.erik.jetpackpro.data.source.remote

import androidx.lifecycle.LiveData
import com.erik.jetpackpro.BuildConfig
import com.erik.jetpackpro.core.Config
import com.erik.jetpackpro.data.source.remote.response.movie.MovieResponse
import com.erik.jetpackpro.data.network.ApiEndPoint
import com.erik.jetpackpro.data.source.remote.response.ApiResponse
import com.erik.jetpackpro.data.source.remote.response.moviedetail.MovieDetailResponse
import com.erik.jetpackpro.data.source.remote.RemoteHelper.call

class MovieRemoteDataSource (
        private val apiEndPoint: ApiEndPoint
) : MovieDataSourceInterface {
    override fun getDiscoverMovie(): LiveData<ApiResponse<MovieResponse>> {
        return call(apiEndPoint.getDiscoverMovie(
                BuildConfig.API_KEY,
                Config.language,
                Config.sort_by
        ))
    }

    override fun getMovieDetail(id: Int): LiveData<ApiResponse<MovieDetailResponse>> {
        return call(apiEndPoint.getMovieDetail(id,
            BuildConfig.API_KEY,
            Config.append_to_response))
    }
}