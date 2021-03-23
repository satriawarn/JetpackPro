package com.erik.jetpackpro.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.erik.jetpackpro.R
import com.erik.jetpackpro.core.View
import com.erik.jetpackpro.databinding.ActivityMainBinding
import com.erik.jetpackpro.ui.favorite.FavoriteActivity
import com.erik.jetpackpro.ui.movies.detail.MovieDetail
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : View() {
    private var doubleClickExit = false
    private lateinit var activityMainBinding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        activityMainBinding.viewPager.adapter = sectionsPagerAdapter
        activityMainBinding.tabs.setupWithViewPager(activityMainBinding.viewPager)

        supportActionBar?.elevation = 0f

        btn_favorite.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (doubleClickExit) {
            super.onBackPressed()
            return
        }

        this.doubleClickExit = true
        Toast.makeText(this, resources.getString(R.string.double_click_exit), Toast.LENGTH_SHORT)
                .show()

        Handler(Looper.getMainLooper()).postDelayed({ doubleClickExit = false }, 2000)
    }
}