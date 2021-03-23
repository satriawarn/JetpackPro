package com.erik.jetpackpro.utils

import com.erik.jetpackpro.data.source.remote.response.movie.MovieResponse
import com.erik.jetpackpro.data.source.remote.response.moviedetail.MovieDetailResponse
import com.erik.jetpackpro.data.source.remote.response.tv.TvResponse
import com.erik.jetpackpro.data.source.remote.response.tvdetail.TvDetailResponse
import com.google.gson.Gson
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class DataDummy {
    fun getMovies(): MovieResponse {
        return getFromJson("movies_local.json", MovieResponse::class.java)
    }

    fun getMovie(): MovieDetailResponse {
        return getFromJson("movie_detail_local.json", MovieDetailResponse::class.java)
    }

    fun getTVShows(): TvResponse {
        return getFromJson("tvshows_local.json", TvResponse::class.java)
    }

    fun getTVShow(): TvDetailResponse {
        return getFromJson("tv_detail_local.json", TvDetailResponse::class.java)
    }

    private fun <T> getFromJson(filename: String, type: Class<T>): T {
        val testFolderResources = File(File("").absolutePath, "src/test/resources")
        val jsonFile = File(testFolderResources.absolutePath, filename)
        val iStream = FileInputStream(jsonFile)

        val iReader = InputStreamReader(iStream)
        val data = Gson().fromJson(iReader, type)

        iReader.close()
        iStream.close()

        return data
    }
}