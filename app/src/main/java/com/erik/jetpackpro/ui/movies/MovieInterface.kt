package com.erik.jetpackpro.ui.movies

import com.erik.jetpackpro.data.source.local.entity.MovieEntity

interface MovieInterface {
    fun onItemClick(movie: MovieEntity)
}