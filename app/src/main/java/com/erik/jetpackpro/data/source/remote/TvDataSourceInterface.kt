package com.erik.jetpackpro.data.source.remote

import androidx.lifecycle.LiveData
import com.erik.jetpackpro.data.source.remote.response.ApiResponse
import com.erik.jetpackpro.data.source.remote.response.tv.TvResponse
import com.erik.jetpackpro.data.source.remote.response.tvdetail.TvDetailResponse

interface TvDataSourceInterface {
    fun getDiscoverTv(): LiveData<ApiResponse<TvResponse>>
    fun getTVShowDetail(id: Int): LiveData<ApiResponse<TvDetailResponse>>
}