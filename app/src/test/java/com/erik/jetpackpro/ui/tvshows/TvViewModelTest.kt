import com.erik.jetpackpro.ui.tvshows.TvViewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.erik.jetpackpro.data.source.remote.response.tv.TvResponse
import com.erik.jetpackpro.data.repository.TvRepository
import com.erik.jetpackpro.data.source.local.entity.TvShowEntity
import com.erik.jetpackpro.utils.Resource
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.InputStreamReader
import org.mockito.Mockito.`when`

@RunWith(MockitoJUnitRunner::class)
class TvViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var tvViewModel: TvViewModel

    @Mock
    private lateinit var repository: TvRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvShowEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        tvViewModel = TvViewModel(repository)
    }

    @Test
    fun getTvShows() {
        val dummyTvShow = Resource.success(pagedList)
        val randomNumber = (0 until 100).random()
        `when` (dummyTvShow.data?.size).thenReturn(randomNumber)

        val tvShow = MutableLiveData<Resource<PagedList<TvShowEntity>>>()
        tvShow.value = dummyTvShow

        `when`(repository.getDiscoverTv()).thenReturn(tvShow)
        val tvEntity = tvViewModel.getDiscoverTv().value?.data

        verify(repository).getDiscoverTv()
        assertNotNull(tvEntity)
        assertEquals(randomNumber, tvEntity?.size)

        tvViewModel.getDiscoverTv().observeForever(observer)
        verify(observer).onChanged(dummyTvShow)

    }
}