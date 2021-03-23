package com.erik.jetpackpro.di

import com.erik.jetpackpro.ui.favorite.movie.FavoriteMovieViewModel
import com.erik.jetpackpro.ui.favorite.tvshow.FavoriteTvViewModel
import com.erik.jetpackpro.ui.movies.MovieViewModel
import com.erik.jetpackpro.ui.movies.detail.MovieDetailViewModel
import com.erik.jetpackpro.ui.tvshows.TvViewModel
import com.erik.jetpackpro.ui.tvshows.detail.TvDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { MovieViewModel(get())}
    viewModel { MovieDetailViewModel(get()) }

    viewModel { TvViewModel(get()) }
    viewModel { TvDetailViewModel(get()) }

    viewModel { FavoriteMovieViewModel(get()) }
    viewModel { FavoriteTvViewModel(get()) }
}