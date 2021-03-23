package com.erik.jetpackpro.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.erik.jetpackpro.data.source.local.entity.MovieDetailEntity
import com.erik.jetpackpro.data.source.local.entity.MovieEntity
import com.erik.jetpackpro.utils.Resource

interface MovieRepositoryInterface {
    fun getDiscoverMovie(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getMovieDetail(id: Int): LiveData<Resource<MovieDetailEntity>>
    fun getFavoriteMovies(): LiveData<PagedList<MovieDetailEntity>>
    fun setFavoriteMovie(movie: MovieDetailEntity, state: Boolean)
}