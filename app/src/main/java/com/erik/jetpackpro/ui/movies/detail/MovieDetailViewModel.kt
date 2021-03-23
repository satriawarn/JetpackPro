package com.erik.jetpackpro.ui.movies.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.erik.jetpackpro.data.repository.MovieRepository
import com.erik.jetpackpro.data.source.local.entity.MovieDetailEntity
import com.erik.jetpackpro.utils.Resource

class MovieDetailViewModel (
        private val movieDetailRepository: MovieRepository
) : ViewModel() {

    private var id = MutableLiveData<Int>()

    fun setMovieId(id: Int) {
        this.id.value = id
    }

    var movie: LiveData<Resource<MovieDetailEntity>> =
        Transformations.switchMap(id) { mId ->
            movieDetailRepository.getMovieDetail(mId)
        }

    fun setFavorite() {
        val movieResource = movie.value

        if (movieResource != null) {
            val movieData = movieResource.data

            movieData?.let {
                movieDetailRepository.setFavoriteMovie(it, !it.favorite)
            }
        }
    }
}