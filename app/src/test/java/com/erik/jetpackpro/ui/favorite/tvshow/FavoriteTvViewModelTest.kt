package com.erik.jetpackpro.ui.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.erik.jetpackpro.data.repository.TvRepository
import com.erik.jetpackpro.data.source.local.entity.TvDetailEntity
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteTvViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var favoriteTvViewModel: FavoriteTvViewModel

    @Mock
    private lateinit var repository: TvRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvDetailEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvDetailEntity>

    @Before
    fun setUp() {
        favoriteTvViewModel = FavoriteTvViewModel(repository)
    }

    @Test
    fun getFavoriteTv() {
        val dummyTvShows = pagedList
        val randomNumber = (0 until 100).random()
        `when`(dummyTvShows.size).thenReturn(randomNumber)

        val tvShows = MutableLiveData<PagedList<TvDetailEntity>>()
        tvShows.value = dummyTvShows

        `when`(repository.getFavoriteTVShows()).thenReturn(tvShows)
        val tvShowEntity = favoriteTvViewModel.getFavoriteTv().value

        verify(repository).getFavoriteTVShows()
        assertNotNull(tvShowEntity)
        assertEquals(randomNumber, tvShowEntity?.size)

        favoriteTvViewModel.getFavoriteTv().observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }

    @Test
    fun deleteFavorite() {
        val dummyTvShows = pagedList
        `when`(dummyTvShows.size).thenReturn(5)
        val courses = MutableLiveData<PagedList<TvDetailEntity>>()
        courses.value = dummyTvShows

        `when`(repository.getFavoriteTVShows()).thenReturn(courses)
        val tvEntities = favoriteTvViewModel.getFavoriteTv().value
        verify(repository).getFavoriteTVShows()
        assertNotNull(tvEntities)
        assertEquals(5, tvEntities?.size)

        favoriteTvViewModel.getFavoriteTv().observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }
}