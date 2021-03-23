package com.erik.jetpackpro.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.erik.jetpackpro.data.source.local.entity.MovieEntity
import com.erik.jetpackpro.data.source.local.entity.TvShowEntity

@Dao
interface TvShowDao {
    @Query("SELECT * FROM tv ORDER BY popularity DESC")
    fun getDiscoverTv(): DataSource.Factory<Int, TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(movies: List<TvShowEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)
}