package com.erik.jetpackpro.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.erik.jetpackpro.data.source.local.entity.TvDetailEntity
import com.erik.jetpackpro.data.source.local.entity.TvShowEntity
import com.erik.jetpackpro.data.source.local.room.AppDatabase

class TvShowLocalDataSource(database: AppDatabase) {
    private var tvShowDao = database.tvShowDao()

    private var tvDetailDao = database.tvDetailDao()

    fun getDiscoverTv(): DataSource.Factory<Int, TvShowEntity> {
        return tvShowDao.getDiscoverTv()
    }

    fun insertTVShows(tvShows: List<TvShowEntity>) {
        return tvShowDao.insertAll(tvShows)
    }

    fun insertDetailTv(tvDetailEntity: TvDetailEntity) {
        return tvDetailDao.insertDetailTv(tvDetailEntity)
    }

    fun getTvDetail(id: Int): LiveData<TvDetailEntity> {
        return tvDetailDao.getTvDetail(id)
    }

    fun setFavoriteTv(tvDetailEntity: TvDetailEntity, newState: Boolean) {
        tvDetailEntity.favorite = newState
        tvDetailDao.updateMovie(tvDetailEntity)
    }

    fun getFavoriteTVShows(): DataSource.Factory<Int, TvDetailEntity> {
        return tvDetailDao.getFavoriteTv()
    }
}