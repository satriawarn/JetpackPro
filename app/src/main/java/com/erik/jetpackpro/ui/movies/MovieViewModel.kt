package com.erik.jetpackpro.ui.movies

import androidx.lifecycle.ViewModel
import com.erik.jetpackpro.data.repository.MovieRepository

class MovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    fun getDiscoverMovie() = movieRepository.getDiscoverMovie()
}
