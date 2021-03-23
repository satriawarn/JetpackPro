package com.erik.jetpackpro.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.erik.jetpackpro.data.source.local.entity.MovieDetailEntity
import com.erik.jetpackpro.data.source.local.entity.MovieEntity

@Dao
interface MovieDetailDao {
    @Query("SELECT * FROM movie_detail WHERE id = :id")
    fun getMovieDetail(id: Int): LiveData<MovieDetailEntity>

    @Query("SELECT * FROM movie_detail WHERE favorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailMovie(movieDetailEntity: MovieDetailEntity)

    @Update
    fun updateMovie(movie: MovieDetailEntity)
}