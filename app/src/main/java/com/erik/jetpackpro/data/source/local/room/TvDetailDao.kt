package com.erik.jetpackpro.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.erik.jetpackpro.data.source.local.entity.MovieDetailEntity
import com.erik.jetpackpro.data.source.local.entity.TvDetailEntity
import com.erik.jetpackpro.data.source.local.entity.TvShowEntity

@Dao
interface TvDetailDao {
    @Query("SELECT * FROM tv_detail WHERE id = :id")
    fun getTvDetail(id: Int): LiveData<TvDetailEntity>

    @Query("SELECT * FROM tv_detail WHERE favorite = 1")
    fun getFavoriteTv(): DataSource.Factory<Int, TvDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailTv(tvDetailEntity: TvDetailEntity)

    @Update
    fun updateMovie(tvDetailEntity: TvDetailEntity)
}