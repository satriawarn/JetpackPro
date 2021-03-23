package com.erik.jetpackpro.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.erik.jetpackpro.data.repository.MovieRepository
import com.erik.jetpackpro.data.source.local.entity.MovieDetailEntity

class FavoriteMovieViewModel(
        private val movieRepository: MovieRepository
) : ViewModel() {
    fun getFavoriteMovie(): LiveData<PagedList<MovieDetailEntity>>{
        return movieRepository.getFavoriteMovies()
    }

    fun deleteFavorite(movieDetailEntity: MovieDetailEntity) {
        val newState = !movieDetailEntity.favorite
        movieRepository.setFavoriteMovie(movieDetailEntity, newState)
    }
}