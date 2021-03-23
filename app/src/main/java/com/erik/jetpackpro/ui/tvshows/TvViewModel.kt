package com.erik.jetpackpro.ui.tvshows

import androidx.lifecycle.ViewModel
import com.erik.jetpackpro.data.repository.TvRepository

class TvViewModel(
    private val tvRepository: TvRepository
) : ViewModel() {
    fun getDiscoverTv() = tvRepository.getDiscoverTv()
}