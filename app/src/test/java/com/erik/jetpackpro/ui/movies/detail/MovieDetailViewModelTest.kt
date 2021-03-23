package com.erik.jetpackpro.ui.movies.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.erik.jetpackpro.data.repository.MovieRepository
import com.erik.jetpackpro.data.source.local.entity.MovieDetailEntity
import com.erik.jetpackpro.data.source.remote.response.moviedetail.MovieDetailResponse
import com.erik.jetpackpro.utils.DataDummy
import com.erik.jetpackpro.utils.Resource
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.InputStreamReader

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {
    private val dataDummy = DataDummy()
    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private val sampleDetailMovie = dataDummy.getMovie()
    private val sampleMovieId = sampleDetailMovie.id!!
    private val movieDetailEntity = MovieDetailEntity.fromMovieDetailResponse(sampleDetailMovie)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<MovieDetailEntity>>

    @Before
    fun setUp() {
        movieDetailViewModel = MovieDetailViewModel(repository)
        movieDetailViewModel.setMovieId(sampleMovieId)
    }

    @Test
    fun getMovieDetail() {
        val movieResource = Resource.success(movieDetailEntity)
        val movieDetailLive = MutableLiveData<Resource<MovieDetailEntity>>()
        movieDetailLive.value = movieResource
        `when`(repository.getMovieDetail(sampleMovieId)).thenReturn(movieDetailLive)

        movieDetailViewModel.movie.observeForever(observer)
        verify(observer).onChanged(movieResource)

        val movie = movieDetailViewModel.movie.value?.data

        assertNotNull(movie)
        assertEquals(sampleDetailMovie.id, movie?.id)
        assertEquals(sampleDetailMovie.title, movie?.title)
        assertEquals(sampleDetailMovie.overview, movie?.overview)
        assertEquals(sampleDetailMovie.posterPath, movie?.posterPath)
        assertEquals(sampleDetailMovie.releaseDate, movie?.releaseDate)
        assertEquals(sampleDetailMovie.voteCount, movie?.voteCount)

        assertEquals(sampleDetailMovie.voteAverage as Double, movie?.voteAverage as Double, 0.0001)
    }

    @Test
    fun setFavorite() {
        val movieResource = Resource.success(movieDetailEntity)
        val movieDetailLive = MutableLiveData<Resource<MovieDetailEntity>>()
        movieDetailLive.value = movieResource
        `when`(repository.getMovieDetail(sampleMovieId)).thenReturn(movieDetailLive)

        movieDetailViewModel.movie.observeForever(observer)
        verify(observer).onChanged(movieResource)

        val movie = movieDetailViewModel.setFavorite()
        assertNotNull(movie)
    }
}