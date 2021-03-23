package com.erik.jetpackpro.ui.tvshows.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.erik.jetpackpro.data.repository.TvRepository
import com.erik.jetpackpro.data.source.local.entity.TvDetailEntity
import com.erik.jetpackpro.utils.Resource

class TvDetailViewModel (
        private val tvDetailRepository: TvRepository
) : ViewModel() {
    private var id = MutableLiveData<Int>()

    fun setMovieId(id: Int) {
        this.id.value = id
    }

    var tvShow: LiveData<Resource<TvDetailEntity>> =
        Transformations.switchMap(id) { mId ->
            tvDetailRepository.getTvDetail(mId)
        }

    fun setFavorite() {
        val movieResource = tvShow.value

        if (movieResource != null) {
            val movieData = movieResource.data

            movieData?.let {
                tvDetailRepository.setFavoriteTVShow(it, !it.favorite)
            }
        }
    }
}