package com.erik.jetpackpro.ui.main

import android.content.Context

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.erik.jetpackpro.R
import com.erik.jetpackpro.data.network.ApiClient
import com.erik.jetpackpro.data.source.remote.MovieRemoteDataSource
import com.erik.jetpackpro.data.source.remote.TvRemoteDataSource
import com.erik.jetpackpro.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test


class MainActivityTest {
    private val apiEndPoint = ApiClient()
    private val movieSource = MovieRemoteDataSource(apiEndPoint.getApiService())
    private val tvSource = TvRemoteDataSource(apiEndPoint.getApiService())

    private val sampleMovies = movieSource.getDiscoverMovie()
    private val sampleTvShows = tvSource.getDiscoverTv()

    private lateinit var instrumentalContext: Context

    @Before
    fun setUp() {
        instrumentalContext = InstrumentationRegistry.getInstrumentation().targetContext
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())

        ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            sampleMovies.value?.body?.results?.let {
                scrollToPosition<RecyclerView.ViewHolder>(
                    sampleMovies.value!!.body.results.lastIndex
                )
            }
        )
    }

    @Test
    fun loadMovieDetail() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))

        val dummyMovie = sampleMovies.value?.body?.results!!
        val sampleMovie = dummyMovie.first()
        val position = dummyMovie.indexOf(sampleMovie)

        onView(withId(R.id.rv_movie)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click())
        )

        onView(withId(R.id.tvName)).check(matches(isDisplayed()))
        onView(withId(R.id.tvName)).check(matches(withText(sampleMovie.title)))
        onView(withId(R.id.tvDescription)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDescription)).check(matches(withText(sampleMovie.overview)))
        onView(withId(R.id.tvReleaseDate)).check(matches(isDisplayed()))
        onView(withId(R.id.tvReleaseDate)).check(matches(withText("Release Date : ${sampleMovie.releaseDate}")))
    }

    @Test
    fun loadTvShows() {
        onView(withText("Tv Shows")).perform(click())
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv)).perform(
            sampleTvShows.value?.body?.results?.let {
                scrollToPosition<RecyclerView.ViewHolder>(
                    sampleTvShows.value!!.body.results.lastIndex
                )
            }
        )
    }

    @Test
    fun loadTvShowDetail() {
        onView(withText("Tv Shows")).perform(click())
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()))

        val dummyTv = sampleTvShows.value?.body?.results!!
        val sampleTv = dummyTv.first()
        val position = dummyTv.indexOf(sampleTv)

        onView(withId(R.id.rv_tv)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click())
        )

        onView(withId(R.id.tvNameTv)).check(matches(isDisplayed()))
        onView(withId(R.id.tvNameTv)).check(matches(withText(sampleTv.name)))
        onView(withId(R.id.tvDescriptionTv)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDescriptionTv)).check(matches(withText(sampleTv.overview)))
        onView(withId(R.id.tvReleaseDateTv)).check(matches(isDisplayed()))
        onView(withId(R.id.tvReleaseDateTv)).check(matches(withText("First Air Date : ${sampleTv.firstAirDate}")))
    }

    @Test
    fun favoriteMovie() {
        onView(withText("Movies")).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))

        val res = sampleMovies.value?.body?.results!!
        val sampleMovie = res.first()
        val position = res.indexOf(sampleMovie)

        onView(withId(R.id.rv_movie)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click())
        )

        onView(withId(R.id.favorite)).perform(click())
        onView(isRoot()).perform(pressBack())

        onView(withText("Favorite")).perform(click())

        onView(withId(R.id.rv_movie_favorite)).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click())
        )

        onView(withId(R.id.tvName)).check(matches(isDisplayed()))
        onView(withId(R.id.tvName)).check(matches(withText(sampleMovie.title)))
        onView(withId(R.id.tvDescription)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDescription)).check(matches(withText(sampleMovie.overview)))
        onView(withId(R.id.tvReleaseDate)).check(matches(isDisplayed()))
        onView(withId(R.id.tvReleaseDate)).check(matches(withText("Release Date : ${sampleMovie.releaseDate}")))
    }

    @Test
    fun favoriteTv() {
        onView(withText("Tv Shows")).perform(click())
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()))

        val res = sampleTvShows.value?.body?.results!!
        val sampleTv = res.first()
        val position = res.indexOf(sampleTv)

        onView(withId(R.id.rv_tv)).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click())
        )

        onView(withId(R.id.favorite_tv)).perform(click())
        onView(isRoot()).perform(pressBack())

        onView(withText("Favorite")).perform(click())

        onView(withText("Tv Shows")).perform(click())
        onView(withId(R.id.rv_tv_favorite)).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click())
        )

        onView(withId(R.id.tvNameTv)).check(matches(isDisplayed()))
        onView(withId(R.id.tvNameTv)).check(matches(withText(sampleTv.name)))
        onView(withId(R.id.tvDescriptionTv)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDescriptionTv)).check(matches(withText(sampleTv.overview)))
        onView(withId(R.id.tvReleaseDateTv)).check(matches(isDisplayed()))
        onView(withId(R.id.tvReleaseDateTv)).check(matches(withText("First Air Date : ${sampleTv.firstAirDate}")))
    }
}