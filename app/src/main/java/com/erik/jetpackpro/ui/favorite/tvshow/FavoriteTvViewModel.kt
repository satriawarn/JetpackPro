package com.erik.jetpackpro.ui.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.erik.jetpackpro.data.repository.TvRepository
import com.erik.jetpackpro.data.source.local.entity.TvDetailEntity

class FavoriteTvViewModel(
        private val tvRepository: TvRepository
): ViewModel() {
    fun getFavoriteTv(): LiveData<PagedList<TvDetailEntity>>{
        return tvRepository.getFavoriteTVShows()
    }

    fun deleteFavorite(tvDetailEntity: TvDetailEntity){
        val newState = !tvDetailEntity.favorite
        tvRepository.setFavoriteTVShow(tvDetailEntity, newState)
    }
}