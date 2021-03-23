package com.erik.jetpackpro.ui.movies.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View.GONE
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import com.erik.jetpackpro.BuildConfig
import com.erik.jetpackpro.R
import com.erik.jetpackpro.core.View
import com.erik.jetpackpro.data.source.remote.response.moviedetail.MovieGenres
import com.erik.jetpackpro.data.source.remote.response.moviedetail.ProductionCompany
import com.erik.jetpackpro.databinding.ActivityMovieDetailBinding
import com.erik.jetpackpro.databinding.ContentDetailMovieBinding
import com.erik.jetpackpro.ui.webview.WebViewActivity
import com.erik.jetpackpro.utils.SnackBar
import com.erik.jetpackpro.utils.Status
import com.erik.jetpackpro.utils.loadImage
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.content_detail_movie.*
import kotlinx.android.synthetic.main.loading_genres.*
import kotlinx.android.synthetic.main.loading_production.*
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetail : View() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"

        fun fromMinutesToHHmm(minutes: Int): String {
            val hours = TimeUnit.MINUTES.toHours(minutes.toLong())
            val remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours)
            return String.format("%2d h %2d min", hours, remainMinutes)
        }

        var movieTitle: String? = null
    }

    private val movieDetailViewModel: MovieDetailViewModel by viewModel()

    private val listGenres: MutableList<MovieGenres> = ArrayList()

    private val listProductions: MutableList<ProductionCompany> = ArrayList()

    private val adapterGenres: AdapterGenres by lazy {
        AdapterGenres(listGenres)
    }

    private val adapterProduction: AdapterProduction by lazy {
        AdapterProduction(listProductions)
    }

    private lateinit var detailMovieBinding: ContentDetailMovieBinding

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityMovieDetailBinding = ActivityMovieDetailBinding.inflate(layoutInflater)
        detailMovieBinding = activityMovieDetailBinding.contentDetail

        setContentView(activityMovieDetailBinding.root)

        setSupportActionBar(toolbar)
        title(supportActionBar!!, "")

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            setListener()
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        } else {
            supportActionBar?.setHomeAsUpIndicator(
                ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
            )
            title(supportActionBar!!, "")
        }

        populateMovieDetail()

        favorite.setOnClickListener {
            movieDetailViewModel.setFavorite()
            showNotification()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    fun title(actionBar: ActionBar?, title: String) {
        actionBar?.title = title
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setListener() {
        app_bar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout!!.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    ctlDetail.title = movieTitle
                    supportActionBar?.setHomeAsUpIndicator(
                        ContextCompat.getDrawable(applicationContext, R.drawable.ic_arrow_back)
                    )
                    isShow = true
                } else if (isShow) {
                    ctlDetail.title = " "
                    supportActionBar?.setHomeAsUpIndicator(
                        ContextCompat.getDrawable(applicationContext, R.drawable.ic_back_overide)
                    )
                    isShow = false
                }
            }
        })
    }

    private fun populateMovieDetail() {
        intent.extras?.let {
            val movieId = it.getInt(EXTRA_MOVIE)

            shImage.startShimmer()
            shProductions.startShimmer()
            shGenres.startShimmer()

            movieDetailViewModel.setMovieId(movieId)

            movieDetailViewModel.movie.observe(this) { movieDetail ->
                when (movieDetail.status) {
                    Status.LOADING -> {
                        shImage.startShimmer()
                        shProductions.startShimmer()
                        shGenres.startShimmer()
                    }
                    Status.SUCCESS -> {
                        shImage.stopShimmer()
                        shImage.visibility = GONE
                        shGenres.stopShimmer()
                        shGenres.visibility = GONE
                        shProductions.stopShimmer()
                        shProductions.visibility = GONE

                        ivImage.loadImage(BuildConfig.BASE_URL_POSTER_PATH_BIG + movieDetail.data?.backdropPath)

                        listGenres.clear()
                        movieDetail.data?.genres?.let { it1 -> listGenres.addAll(it1) }
                        adapterGenres.notifyDataSetChanged()

                        listProductions.clear()
                        movieDetail.data?.productionCompany?.let { it1 -> listProductions.addAll(it1) }
                        adapterProduction.notifyDataSetChanged()

                        movieTitle = movieDetail.data?.title

                        rbRatting.rating = movieDetail.data?.voteAverage!!.toFloat()
                        tvRating.text = movieDetail.data.voteAverage.toString()
                        tvCount.text =
                            StringBuilder(movieDetail.data.voteCount.toString() + " Reviews")
                        tvName.text = movieDetail.data.title
                        tvTagLine.text = movieDetail.data.tagline
                        tvReleaseDate.text =
                            resources.getString(R.string.releasedate, movieDetail.data.releaseDate)

                        tvHomepage.setOnClickListener {
                            val intent = Intent(this, WebViewActivity::class.java)
                            intent.putExtra("url", movieDetail.data.homepage)
                            startActivity(intent)
                        }
                        tvDescription.text = movieDetail.data.overview
                        tvRuntime.text =
                            movieDetail.data.runtime.let { it1 -> fromMinutesToHHmm(it1) }

                        rvGenres.also {
                            detailMovieBinding.rvGenres.adapter = adapterGenres
                        }

                        rvProduction.also {
                            detailMovieBinding.rvProduction.adapter = adapterProduction
                        }
                    }
                    Status.ERROR -> {
                        shImage.stopShimmer()
                        shImage.visibility = GONE
                        shGenres.stopShimmer()
                        shGenres.visibility = GONE
                        shProductions.stopShimmer()
                        shProductions.visibility = GONE
                        SnackBar.showSnackBar(
                            detailMovieBinding.root,
                            resources.getString(R.string.something_wrong)
                        )
                    }
                }
            }
        }
    }

    private fun showNotification() {
        val isFavorite = movieDetailViewModel.movie.value?.data?.favorite ?: false
        val message: String
        message = if (isFavorite) {
            resources.getString(R.string.delete_favortie)
        } else {
            resources.getString(R.string.add_favorite)
        }
        detailMovieBinding.root.let { SnackBar.showSnackBar(it, message) }
    }
}