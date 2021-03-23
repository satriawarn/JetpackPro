package com.erik.jetpackpro.ui.tvshows.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View.*
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import com.erik.jetpackpro.BuildConfig
import com.erik.jetpackpro.R
import com.erik.jetpackpro.core.View
import com.erik.jetpackpro.data.source.remote.response.tvdetail.GenresItem
import com.erik.jetpackpro.data.source.remote.response.tvdetail.ProductionCompaniesItem
import com.erik.jetpackpro.databinding.ActivityTvDetailBinding
import com.erik.jetpackpro.databinding.ContentDetailTvBinding
import com.erik.jetpackpro.ui.webview.WebViewActivity
import com.erik.jetpackpro.utils.SnackBar
import com.erik.jetpackpro.utils.Status
import com.erik.jetpackpro.utils.loadImage
import com.example.awesomedialog.*
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_movie_detail.app_bar
import kotlinx.android.synthetic.main.activity_movie_detail.ctlDetail
import kotlinx.android.synthetic.main.activity_movie_detail.shImage
import kotlinx.android.synthetic.main.activity_movie_detail.toolbar
import kotlinx.android.synthetic.main.activity_tv_detail.*
import kotlinx.android.synthetic.main.content_detail_movie.rvGenres
import kotlinx.android.synthetic.main.content_detail_movie.rvProduction
import kotlinx.android.synthetic.main.content_detail_tv.*
import kotlinx.android.synthetic.main.loading_episode.*
import kotlinx.android.synthetic.main.loading_genres.*
import kotlinx.android.synthetic.main.loading_production.*
import java.lang.StringBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvDetail : View() {

    companion object {
        const val EXTRA_TV = "extra_tv"

        var tvTitle: String? = null
    }

    private val tvDetailViewModel: TvDetailViewModel by viewModel()

    private val listGenres: MutableList<GenresItem> = ArrayList()

    private val listProductions: MutableList<ProductionCompaniesItem> = ArrayList()

    private val adapterGenres: AdapterTvGenres by lazy {
        AdapterTvGenres(listGenres)
    }

    private val adapterProduction: AdapterTvProduction by lazy {
        AdapterTvProduction(listProductions)
    }

    private lateinit var detailTvBinding: ContentDetailTvBinding

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityTvDetailBinding = ActivityTvDetailBinding.inflate(layoutInflater)
        detailTvBinding = activityTvDetailBinding.contentDetail

        setContentView(activityTvDetailBinding.root)

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

        populateTvDetail()

        favorite_tv.setOnClickListener {
            tvDetailViewModel.setFavorite()
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
                    ctlDetail.title = tvTitle
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

    private fun populateTvDetail() {
        intent.extras?.let {
            val tvId = it.getInt(EXTRA_TV)



            tvDetailViewModel.setMovieId(tvId)

            tvDetailViewModel.tvShow.observe(this) { tvDetail ->
                when (tvDetail.status) {
                    Status.LOADING -> {
                        shImage.startShimmer()
                        shProductions.startShimmer()
                        shGenres.startShimmer()
                        shEpisode.startShimmer()
                    }
                    Status.SUCCESS -> {
                        shImage.stopShimmer()
                        shImage.visibility = GONE
                        shGenres.stopShimmer()
                        shGenres.visibility = GONE
                        shEpisode.stopShimmer()
                        shEpisode.visibility = GONE
                        linearEpisode.visibility = VISIBLE
                        shProductions.stopShimmer()
                        shProductions.visibility = GONE

                        ivImageTv.loadImage(BuildConfig.BASE_URL_POSTER_PATH_BIG + tvDetail.data?.backdropPath)

                        listGenres.clear()
                        tvDetail.data?.genres?.let { it1 -> listGenres.addAll(it1) }
                        adapterGenres.notifyDataSetChanged()

                        listProductions.clear()
                        tvDetail.data?.productionCompany?.let { it1 -> listProductions.addAll(it1) }
                        adapterProduction.notifyDataSetChanged()

                        tvTitle = tvDetail.data?.name

                        rbRattingTv.rating = tvDetail.data?.voteAverage!!.toFloat()
                        tvRatingTV.text = tvDetail.data.voteAverage.toString()
                        tvCountTv.text =
                            StringBuilder(tvDetail.data.voteCount.toString() + " Reviews")
                        tvNameTv.text = tvDetail.data.name
                        tvTagLineTv.text = tvDetail.data.tagline
                        tvHomepageTv.setOnClickListener {
                            val intent = Intent(this, WebViewActivity::class.java)
                            intent.putExtra("url", tvDetail.data.homepage)
                            startActivity(intent)
                        }
                        tvReleaseDateTv.text =
                            resources.getString(R.string.first_air_date, tvDetail.data.firstAirDate)
                        tvTotalEpisodesTv.text = resources.getString(
                            R.string.total_episode,
                            tvDetail.data.numberOfEpisode.toString()
                        )
                        tvTotalSeasonsTv.text = resources.getString(
                            R.string.total_seaons,
                            tvDetail.data.numberOfSeasons.toString()
                        )

                        rvGenres.also {
                            detailTvBinding.rvGenresTv.adapter = adapterGenres
                        }

                        tvDescriptionTv.text = tvDetail.data.overview
                        tvStatusTv.text = tvDetail.data.status

                        if (tvDetail.data.lastEpisode.episodeNumber.toString().isNullOrEmpty()) {
                            SnackBar.showSnackBar(
                                detailTvBinding.root,
                                resources.getString(R.string.null_or_empty)
                            )
                        } else {
                            last_episode.text = resources.getString(
                                R.string.last_episode,
                                tvDetail.data.lastEpisode.episodeNumber.toString()
                            )
                        }

                        if (tvDetail.data.lastEpisode.stillPath.isNullOrBlank()) {
                            SnackBar.showSnackBar(
                                detailTvBinding.root,
                                resources.getString(R.string.null_or_empty)
                            )
                        } else {
                            last_episode_image.loadImage(BuildConfig.BASE_URL_POSTER_PATH_BIG + tvDetail.data.lastEpisode.stillPath)
                        }

                        if (tvDetail.data.lastEpisode.name.isNullOrEmpty()) {
                            SnackBar.showSnackBar(
                                detailTvBinding.root,
                                resources.getString(R.string.null_or_empty)
                            )
                        } else {
                            title_last_episode.text = tvDetail.data.lastEpisode.name
                        }

                        if (tvDetail.data.lastEpisode.overview.isNullOrEmpty()) {
                            SnackBar.showSnackBar(
                                detailTvBinding.root,
                                resources.getString(R.string.null_or_empty)
                            )
                        } else {
                            overview_episode_last.text = tvDetail.data.lastEpisode.overview
                        }

                        if (tvDetail.data.nextEpisode.episodeNumber == 0) {
                            AwesomeDialog.build(this)
                                .title(
                                    resources.getString(R.string.next_episode_null),
                                    titleColor = ContextCompat.getColor(this, android.R.color.black)
                                )
                                .icon(R.drawable.empty)
                                .background(R.drawable.layout_rounded_white)
                                .onPositive(
                                    resources.getString(R.string.OK),
                                    buttonBackgroundColor = R.drawable.layout_rounded_dark_black,
                                    textColor = ContextCompat.getColor(this, android.R.color.white)
                                )
                                .position(AwesomeDialog.POSITIONS.CENTER)

                            constraint2.visibility = INVISIBLE
                        } else {
                            if (tvDetail.data.nextEpisode.stillPath.isNullOrEmpty()) {
                                next_episode_image.visibility = INVISIBLE
                            } else {
                                next_episode_image.loadImage(BuildConfig.BASE_URL_POSTER_PATH_BIG + tvDetail.data.nextEpisode.stillPath)
                            }

                            if (tvDetail.data.nextEpisode.episodeNumber.toString()
                                    .isNullOrEmpty()
                            ) {
                                next_episode.visibility = INVISIBLE
                            } else {
                                next_episode.text = resources.getString(
                                    R.string.next_episode,
                                    tvDetail.data.nextEpisode.episodeNumber.toString()
                                )
                            }

                            if (tvDetail.data.nextEpisode.name.isNullOrEmpty()) {
                                title_next_episode.visibility = INVISIBLE
                            } else {
                                title_next_episode.text = tvDetail.data.nextEpisode.name
                            }

                            if (tvDetail.data.nextEpisode.overview.isNullOrEmpty()) {
                                overview_episode_next.visibility = INVISIBLE
                            } else {
                                overview_episode_next.text = tvDetail.data.nextEpisode.overview
                            }
                        }

                        rvProduction.also {
                            detailTvBinding.rvProductionTv.adapter = adapterProduction
                        }
                    }
                    Status.ERROR -> {
                        shImage.stopShimmer()
                        shImage.visibility = GONE
                        shGenres.stopShimmer()
                        shGenres.visibility = GONE
                        shEpisode.stopShimmer()
                        shEpisode.visibility = GONE
                        linearEpisode.visibility = VISIBLE
                        shProductions.stopShimmer()
                        shProductions.visibility = GONE

                        SnackBar.showSnackBar(
                            detailTvBinding.root,
                            resources.getString(R.string.something_wrong)
                        )
                    }
                }
            }
        }
    }

    private fun showNotification() {
        val isFavorite = tvDetailViewModel.tvShow.value?.data?.favorite ?: false
        val message: String
        message = if (isFavorite) {
            resources.getString(R.string.delete_favortie)
        } else {
            resources.getString(R.string.add_favorite)
        }
        detailTvBinding.root.let { SnackBar.showSnackBar(it, message) }
    }
}