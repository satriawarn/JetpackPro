package com.erik.jetpackpro.ui.tvshows

import com.erik.jetpackpro.data.source.remote.response.tv.ResultsItemTv
import com.erik.jetpackpro.data.source.local.entity.TvShowEntity

interface TvInterface {
    fun onItemClick(tv: TvShowEntity)
}