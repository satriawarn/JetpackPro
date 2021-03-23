package com.erik.jetpackpro.di

import com.erik.jetpackpro.data.network.ApiClient
import com.erik.jetpackpro.data.repository.MovieRepository
import com.erik.jetpackpro.data.repository.TvRepository
import com.erik.jetpackpro.data.source.local.MovieLocalDataSource
import com.erik.jetpackpro.data.source.local.TvShowLocalDataSource
import com.erik.jetpackpro.data.source.local.room.AppDatabase
import com.erik.jetpackpro.data.source.remote.MovieRemoteDataSource
import com.erik.jetpackpro.data.source.remote.TvRemoteDataSource
import com.erik.jetpackpro.utils.AppExecutors
import com.erik.jetpackpro.utils.DataDummy
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModules = module {
    factory { AppDatabase.getInstance(androidContext()) }

    single { ApiClient() }
    single { get<ApiClient>().getApiService() }
    single { AppExecutors() }

    single { MovieRemoteDataSource(get()) }
    single { MovieLocalDataSource(get()) }
    single { TvRemoteDataSource(get()) }
    single { TvShowLocalDataSource(get()) }


    single { MovieRepository(get(), get(), get()) }
    single { TvRepository(get(), get(), get()) }

    single { DataDummy() }
}