package com.erik.jetpackpro.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.erik.jetpackpro.data.source.remote.response.ApiResponse
import com.erik.jetpackpro.data.source.remote.response.tv.TvResponse
import com.erik.jetpackpro.data.source.remote.response.tvdetail.TvDetailResponse
import com.erik.jetpackpro.data.source.NetworkBoundResource
import com.erik.jetpackpro.data.source.local.TvShowLocalDataSource
import com.erik.jetpackpro.data.source.local.entity.TvDetailEntity
import com.erik.jetpackpro.data.source.local.entity.TvShowEntity
import com.erik.jetpackpro.data.source.remote.TvRemoteDataSource
import com.erik.jetpackpro.utils.AppExecutors
import com.erik.jetpackpro.utils.Resource

class TvRepository(
        private val tvRemoteDataSource: TvRemoteDataSource,
        private val local: TvShowLocalDataSource,
        private val executors: AppExecutors
) : TvRepositoryInterface {
    override fun getDiscoverTv(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object :
                NetworkBoundResource<PagedList<TvShowEntity>, TvResponse>(executors) {
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build()

                val localTVShows = local.getDiscoverTv()
                return LivePagedListBuilder(localTVShows, config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<TvResponse>> {
                return tvRemoteDataSource.getDiscoverTv()
            }

            override fun saveCallResult(data: TvResponse) {
                TvShowEntity.fromTVShowsResponse(data).let { local.insertTVShows(it) }
            }
        }.asLiveData()
    }

    override fun getTvDetail(id: Int): LiveData<Resource<TvDetailEntity>> {
        return object : NetworkBoundResource<TvDetailEntity, TvDetailResponse>(executors) {
            override fun loadFromDB(): LiveData<TvDetailEntity> {
                return local.getTvDetail(id)
            }

            override fun shouldFetch(data: TvDetailEntity?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<TvDetailResponse>> {
                return tvRemoteDataSource.getTVShowDetail(id)
            }

            override fun saveCallResult(data: TvDetailResponse) {
                TvDetailEntity.fromTvDetailResponse(data).let { local.insertDetailTv(it) }
            }

        }.asLiveData()
    }

    override fun getFavoriteTVShows(): LiveData<PagedList<TvDetailEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()

        return LivePagedListBuilder(local.getFavoriteTVShows(), config).build()
    }

    override fun setFavoriteTVShow(tvDetailEntity: TvDetailEntity, state: Boolean) {
        return executors.diskIO().execute { local.setFavoriteTv(tvDetailEntity, state) }
    }
}