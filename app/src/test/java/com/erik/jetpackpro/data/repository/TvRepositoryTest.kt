package com.erik.jetpackpro.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.erik.jetpackpro.data.source.local.TvShowLocalDataSource
import com.erik.jetpackpro.data.source.local.entity.TvDetailEntity
import com.erik.jetpackpro.data.source.local.entity.TvShowEntity
import com.erik.jetpackpro.data.source.remote.TvRemoteDataSource
import com.erik.jetpackpro.utils.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.*

@Suppress("UNCHECKED_CAST")
class TvRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(TvRemoteDataSource::class.java)
    private val local = mock(TvShowLocalDataSource::class.java)
    private val appExecutor = mock(AppExecutors::class.java)
    private val dataDummy = DataDummy()

    private val repository = TvRepository(remote, local, appExecutor)

    private val sampleTvResponse = dataDummy.getTVShows()
    private val sampleTvDetailRespone = dataDummy.getTVShow()
    private val sampleTvId = sampleTvDetailRespone.id
    private val sampleTvDetailEntity = TvDetailEntity.fromTvDetailResponse(sampleTvDetailRespone)

    @Test
    fun getDiscoverTv() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getDiscoverTv()).thenReturn(dataSourceFactory)
        repository.getDiscoverTv()

        val tvShowsEntity = TvShowEntity.fromTVShowsResponse(dataDummy.getTVShows())
        val tvShowsPaged = PagedListUtil.mockPagedList(tvShowsEntity)
        val tvShowsResource = Resource.success(tvShowsPaged)

        verify(local).getDiscoverTv()
        assertNotNull(tvShowsResource.data)
        assertEquals(sampleTvResponse.results.size, tvShowsResource.data?.size)
    }

    @Test
    fun getTvDetail() {
        val dummyMovie = MutableLiveData<TvDetailEntity>()
        val tvDetailEntity = TvDetailEntity.fromTvDetailResponse(sampleTvDetailRespone)
        dummyMovie.value = sampleTvDetailEntity
        `when`(local.getTvDetail(sampleTvId)).thenReturn(dummyMovie)

        val tvLive = LiveDataTestUtil.getValue(repository.getTvDetail(sampleTvId))
        verify(local).getTvDetail(sampleTvId)
        assertNotNull(tvLive)
        assertNotNull(tvLive.data)
        assertEquals(tvDetailEntity, tvLive.data)
    }

    @Test
    fun getFavoriteTVShows() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvDetailEntity>
        `when`(local.getFavoriteTVShows()).thenReturn(dataSourceFactory)
        repository.getFavoriteTVShows()

        val tvEntity = TvShowEntity.fromTVShowsResponse(dataDummy.getTVShows())
        val tvPaged = PagedListUtil.mockPagedList(tvEntity)
        val tvResource = Resource.success(tvPaged)

        verify(local).getFavoriteTVShows()
        assertNotNull(tvResource)
        assertEquals(sampleTvResponse.results.size, tvResource.data?.size)
    }

    @Test
    fun setFavoriteTVShow() {
        local.setFavoriteTv(sampleTvDetailEntity, true)

        verify(local).setFavoriteTv(sampleTvDetailEntity, true)
        verifyNoMoreInteractions(local)
    }
}