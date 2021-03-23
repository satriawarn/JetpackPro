package com.erik.jetpackpro.ui.tvshows.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.erik.jetpackpro.data.repository.MovieRepository
import com.erik.jetpackpro.data.source.remote.response.tvdetail.TvDetailResponse
import com.erik.jetpackpro.data.repository.TvRepository
import com.erik.jetpackpro.data.source.local.entity.MovieDetailEntity
import com.erik.jetpackpro.data.source.local.entity.TvDetailEntity
import com.erik.jetpackpro.ui.movies.detail.MovieDetailViewModel
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
class TvDetailViewModelTest {
    private val dataDummy = DataDummy()
    private lateinit var tvDetailViewModel: TvDetailViewModel
    private val sampleDetailTv = dataDummy.getTVShow()
    private val sampleTvId = sampleDetailTv.id
    private val tvDetailEntity = TvDetailEntity.fromTvDetailResponse(sampleDetailTv)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: TvRepository

    @Mock
    private lateinit var observer: Observer<Resource<TvDetailEntity>>

    @Before
    fun setUp() {
        tvDetailViewModel = TvDetailViewModel(repository)
        tvDetailViewModel.setMovieId(sampleTvId)
    }

    @Test
    fun getTvDetail() {
        val tvResource = Resource.success(tvDetailEntity)
        val tvDetailLive = MutableLiveData<Resource<TvDetailEntity>>()
        tvDetailLive.value = tvResource
        `when`(repository.getTvDetail(sampleTvId)).thenReturn(tvDetailLive)

        tvDetailViewModel.tvShow.observeForever(observer)
        verify(observer).onChanged(tvResource)

        val tv = tvDetailViewModel.tvShow.value?.data

        assertNotNull(tv)
        assertEquals(sampleDetailTv.id, tv?.id)
        assertEquals(sampleDetailTv.name, tv?.name)
        assertEquals(sampleDetailTv.overview, tv?.overview)
        assertEquals(sampleDetailTv.posterPath, tv?.posterPath)
        assertEquals(sampleDetailTv.firstAirDate, tv?.firstAirDate)
        assertEquals(sampleDetailTv.voteCount, tv?.voteCount)

        assertEquals(sampleDetailTv.voteAverage, tv?.voteAverage as Double, 0.0001)
    }

    @Test
    fun setFavorite() {
        val tvResource = Resource.success(tvDetailEntity)
        val tvDetailLive = MutableLiveData<Resource<TvDetailEntity>>()
        tvDetailLive.value = tvResource
        `when`(repository.getTvDetail(sampleTvId)).thenReturn(tvDetailLive)

        tvDetailViewModel.tvShow.observeForever(observer)
        verify(observer).onChanged(tvResource)

        val tvShow = tvDetailViewModel.setFavorite()
        assertNotNull(tvShow)
    }
}