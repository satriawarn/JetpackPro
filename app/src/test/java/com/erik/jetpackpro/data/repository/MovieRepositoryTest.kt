package com.erik.jetpackpro.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.erik.jetpackpro.data.source.local.MovieLocalDataSource
import com.erik.jetpackpro.data.source.local.entity.MovieEntity
import com.erik.jetpackpro.data.source.remote.MovieRemoteDataSource
import androidx.paging.DataSource
import com.erik.jetpackpro.data.source.local.entity.MovieDetailEntity
import com.erik.jetpackpro.utils.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.*

@Suppress("UNCHECKED_CAST")
class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(MovieRemoteDataSource::class.java)
    private val local = mock(MovieLocalDataSource::class.java)
    private val executors = mock(AppExecutors::class.java)
    private val dataDummy = DataDummy()

    private val repository = MovieRepository(remote, local, executors)

    private val sampleMoviesResponse = dataDummy.getMovies()
    private val sampleMovieDetailResponse = dataDummy.getMovie()
    private val sampleMovieId = sampleMovieDetailResponse.id!!
    private val sampleMovieDetailEntity =
        MovieDetailEntity.fromMovieDetailResponse(sampleMovieDetailResponse)

    @Test
    fun getDiscoverMovie() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getDiscoverMovie()).thenReturn(dataSourceFactory)
        repository.getDiscoverMovie()

        val movieEntity = MovieEntity.fromMoviesResponse(dataDummy.getMovies())
        val moviesPaged = PagedListUtil.mockPagedList(movieEntity)
        val movieResource = Resource.success(moviesPaged)

        verify(local).getDiscoverMovie()
        assertNotNull(movieResource.data)
        assertEquals(sampleMoviesResponse.results.size, movieResource.data?.size)
    }

    @Test
    fun getMovieDetail() {
        val dummyMovie = MutableLiveData<MovieDetailEntity>()
        val movieDetailEntity = MovieDetailEntity.fromMovieDetailResponse(sampleMovieDetailResponse)
        dummyMovie.value = movieDetailEntity
        `when`(local.getMovieDetail(sampleMovieId)).thenReturn(dummyMovie)

        val movieLive = LiveDataTestUtil.getValue(repository.getMovieDetail(sampleMovieId))
        verify(local).getMovieDetail(sampleMovieId)
        assertNotNull(movieLive)
        assertNotNull(movieLive.data)
        assertEquals(movieDetailEntity, movieLive.data)
    }

    @Test
    fun getFavoriteMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieDetailEntity>
        `when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        repository.getFavoriteMovies()

        val moviesEntity = MovieEntity.fromMoviesResponse(dataDummy.getMovies())
        val moviesPaged = PagedListUtil.mockPagedList(moviesEntity)
        val moviesResource = Resource.success(moviesPaged)

        verify(local).getFavoriteMovies()
        assertNotNull(moviesResource)
        assertEquals(sampleMoviesResponse.results.size, moviesResource.data?.size)
    }

    @Test
    fun setFavoriteMovie() {
        local.setFavoriteMovie(sampleMovieDetailEntity, true)

        verify(local).setFavoriteMovie(sampleMovieDetailEntity, true)
        verifyNoMoreInteractions(local)
    }
}