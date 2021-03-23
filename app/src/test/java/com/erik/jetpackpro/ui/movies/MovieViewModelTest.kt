package com.erik.jetpackpro.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.erik.jetpackpro.data.source.remote.response.movie.MovieResponse
import com.erik.jetpackpro.data.repository.MovieRepository
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.erik.jetpackpro.data.source.local.entity.MovieEntity
import com.erik.jetpackpro.utils.Resource
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase
import org.mockito.Mockito.`when`
import java.io.InputStreamReader

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieViewModel: MovieViewModel

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        movieViewModel = MovieViewModel(repository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = Resource.success(pagedList)
        val randomNumber = (0 until 100).random()
        `when`(dummyMovies.data?.size).thenReturn(randomNumber)

        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dummyMovies

        `when`(repository.getDiscoverMovie()).thenReturn(movies)
        val movieEntity = movieViewModel.getDiscoverMovie().value?.data

        verify(repository).getDiscoverMovie()
        assertNotNull(movieEntity)
        assertEquals(randomNumber, movieEntity?.size)

        movieViewModel.getDiscoverMovie().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}