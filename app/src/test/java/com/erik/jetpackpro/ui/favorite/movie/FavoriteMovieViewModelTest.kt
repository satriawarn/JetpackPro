package com.erik.jetpackpro.ui.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.erik.jetpackpro.data.repository.MovieRepository
import com.erik.jetpackpro.data.source.local.entity.MovieDetailEntity
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteMovieViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var favoriteMovieViewModel: FavoriteMovieViewModel

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieDetailEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieDetailEntity>

    @Before
    fun setUp() {
        favoriteMovieViewModel = FavoriteMovieViewModel(repository)
    }


    @Test
    fun getFavoriteMovie() {
        val dummyMovies = pagedList
        val randomNumber = (0 until 100).random()
        `when`(dummyMovies.size).thenReturn(randomNumber)

        val movies = MutableLiveData<PagedList<MovieDetailEntity>>()
        movies.value = dummyMovies

        `when`(repository.getFavoriteMovies()).thenReturn(movies)
        val moviesEntity = favoriteMovieViewModel.getFavoriteMovie().value

        verify(repository).getFavoriteMovies()
        assertNotNull(moviesEntity)
        assertEquals(randomNumber, moviesEntity?.size)

        favoriteMovieViewModel.getFavoriteMovie().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun deleteFavorite() {
        val dummyMovie = pagedList
        `when`(dummyMovie.size).thenReturn(5)
        val courses = MutableLiveData<PagedList<MovieDetailEntity>>()
        courses.value = dummyMovie

        `when`(repository.getFavoriteMovies()).thenReturn(courses)
        val movieEntities = favoriteMovieViewModel.getFavoriteMovie().value
        verify(repository).getFavoriteMovies()
        assertNotNull(movieEntities)
        assertEquals(5, movieEntities?.size)

        favoriteMovieViewModel.getFavoriteMovie().observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }
}