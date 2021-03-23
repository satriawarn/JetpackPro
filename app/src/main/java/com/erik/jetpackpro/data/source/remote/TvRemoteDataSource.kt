package com.erik.jetpackpro.data.source.remote

import androidx.lifecycle.LiveData
import com.erik.jetpackpro.BuildConfig
import com.erik.jetpackpro.core.Config
import com.erik.jetpackpro.data.source.remote.response.tv.TvResponse
import com.erik.jetpackpro.data.network.ApiEndPoint
import com.erik.jetpackpro.data.source.remote.response.ApiResponse
import com.erik.jetpackpro.data.source.remote.response.tvdetail.TvDetailResponse
import com.erik.jetpackpro.data.source.remote.RemoteHelper.call

class TvRemoteDataSource(
    private val apiEndPoint: ApiEndPoint
) : TvDataSourceInterface {

    override fun getDiscoverTv(): LiveData<ApiResponse<TvResponse>> {
        return call(
            apiEndPoint.getTvDiscover(
                BuildConfig.API_KEY,
                Config.language,
                Config.page,
                Config.sort_by
            )
        )
    }

    override fun getTVShowDetail(id: Int): LiveData<ApiResponse<TvDetailResponse>> {
        return call(
            apiEndPoint.getTvDetail(
                id,
                BuildConfig.API_KEY,
                Config.append_to_response
            )
        )
    }

}