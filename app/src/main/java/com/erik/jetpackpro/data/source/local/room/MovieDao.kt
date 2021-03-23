package com.erik.jetpackpro.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.erik.jetpackpro.data.source.local.entity.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    fun getDiscoverMovie(): DataSource.Factory<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)
}