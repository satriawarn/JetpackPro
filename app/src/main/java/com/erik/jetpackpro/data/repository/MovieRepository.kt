package com.erik.jetpackpro.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.erik.jetpackpro.data.source.remote.response.ApiResponse
import com.erik.jetpackpro.data.source.remote.response.movie.MovieResponse
import com.erik.jetpackpro.data.source.remote.response.moviedetail.MovieDetailResponse
import com.erik.jetpackpro.data.source.NetworkBoundResource
import com.erik.jetpackpro.data.source.local.MovieLocalDataSource
import com.erik.jetpackpro.data.source.local.entity.MovieDetailEntity
import com.erik.jetpackpro.data.source.local.entity.MovieEntity
import com.erik.jetpackpro.data.source.remote.MovieRemoteDataSource
import com.erik.jetpackpro.utils.AppExecutors
import com.erik.jetpackpro.utils.Resource

class MovieRepository(
        private val movieRemoteDataSource: MovieRemoteDataSource,
        private val local: MovieLocalDataSource,
        private val executors: AppExecutors
) : MovieRepositoryInterface {

    override fun getDiscoverMovie(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
                NetworkBoundResource<PagedList<MovieEntity>, MovieResponse>(executors) {
            override fun createCall(): LiveData<ApiResponse<MovieResponse>> {
                return movieRemoteDataSource.getDiscoverMovie()
            }

            override fun saveCallResult(data: MovieResponse) {
                MovieEntity.fromMoviesResponse(data).let { local.insertMovies(it) }
            }

            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build()

                val localDataMovies = local.getDiscoverMovie()
                return LivePagedListBuilder(localDataMovies, config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }
        }.asLiveData()
    }

    override fun getMovieDetail(id: Int): LiveData<Resource<MovieDetailEntity>> {
        return object : NetworkBoundResource<MovieDetailEntity, MovieDetailResponse>(executors) {
            override fun loadFromDB(): LiveData<MovieDetailEntity> {
                return local.getMovieDetail(id)
            }

            override fun shouldFetch(data: MovieDetailEntity?): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> {
                return movieRemoteDataSource.getMovieDetail(id)
            }

            override fun saveCallResult(data: MovieDetailResponse) {
                MovieDetailEntity.fromMovieDetailResponse(data).let { local.insertDetailMovie(it) }
            }
        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<PagedList<MovieDetailEntity>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()

        return LivePagedListBuilder(local.getFavoriteMovies(), config).build()

    }

    override fun setFavoriteMovie(movie: MovieDetailEntity, state: Boolean) {
        executors.diskIO().execute { local.setFavoriteMovie(movie, state) }
    }
}