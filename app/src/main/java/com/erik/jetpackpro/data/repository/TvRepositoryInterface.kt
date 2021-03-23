package com.erik.jetpackpro.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.erik.jetpackpro.data.source.local.entity.TvDetailEntity
import com.erik.jetpackpro.data.source.local.entity.TvShowEntity
import com.erik.jetpackpro.utils.Resource

interface TvRepositoryInterface {
    fun getDiscoverTv(): LiveData<Resource<PagedList<TvShowEntity>>>
    fun getTvDetail(id: Int): LiveData<Resource<TvDetailEntity>>
    fun getFavoriteTVShows(): LiveData<PagedList<TvDetailEntity>>
    fun setFavoriteTVShow(tvDetailEntity: TvDetailEntity, state: Boolean)
}