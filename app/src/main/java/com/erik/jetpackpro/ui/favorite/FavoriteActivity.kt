package com.erik.jetpackpro.ui.favorite

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.erik.jetpackpro.R
import com.erik.jetpackpro.core.View
import com.erik.jetpackpro.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var activityFavoriteBinding: ActivityFavoriteBinding

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_favorite)

        activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(activityFavoriteBinding.root)

        val favoritePagerAdapter = FavoritePagerAdapter(this, supportFragmentManager)
        activityFavoriteBinding.viewPager.adapter = favoritePagerAdapter
        activityFavoriteBinding.tabs.setupWithViewPager(activityFavoriteBinding.viewPager)

    }
}