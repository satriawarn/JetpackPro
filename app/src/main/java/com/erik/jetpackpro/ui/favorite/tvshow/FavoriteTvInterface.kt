package com.erik.jetpackpro.ui.favorite.tvshow

import com.erik.jetpackpro.data.source.local.entity.TvDetailEntity

interface FavoriteTvInterface {
    fun onItemTvDetailClick(tv: TvDetailEntity)
}