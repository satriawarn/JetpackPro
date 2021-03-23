package com.erik.jetpackpro.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.erik.jetpackpro.data.source.local.entity.MovieDetailEntity
import com.erik.jetpackpro.data.source.local.entity.MovieEntity
import com.erik.jetpackpro.data.source.local.room.AppDatabase
import com.erik.jetpackpro.ui.movies.detail.MovieDetail

class MovieLocalDataSource(
        database: AppDatabase
) {
    private var movieDao = database.movieDao()

    private var movieDetailDao = database.movieDetailDao()

    fun getDiscoverMovie(): DataSource.Factory<Int, MovieEntity> {
        return movieDao.getDiscoverMovie()
    }

    fun insertMovies(movies: List<MovieEntity>) {
        return movieDao.insertAll(movies)
    }

    fun updateMovie(movieEntity: MovieEntity) {
        movieDao.updateMovie(movieEntity)
    }

    fun insertDetailMovie(movieDetailEntity: MovieDetailEntity) {
        return movieDetailDao.insertDetailMovie(movieDetailEntity)
    }

    fun getMovieDetail(id: Int): LiveData<MovieDetailEntity> {
        return movieDetailDao.getMovieDetail(id)
    }

    fun setFavoriteMovie(movieDetailEntity: MovieDetailEntity, newState: Boolean) {
        movieDetailEntity.favorite = newState
        movieDetailDao.updateMovie(movieDetailEntity)
    }

    fun getFavoriteMovies(): DataSource.Factory<Int, MovieDetailEntity> {
        return movieDetailDao.getFavoriteMovies()
    }
}