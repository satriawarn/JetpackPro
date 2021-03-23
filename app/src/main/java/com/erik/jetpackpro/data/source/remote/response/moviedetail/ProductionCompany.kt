package com.erik.jetpackpro.data.source.remote.response.moviedetail

import com.google.gson.annotations.SerializedName

data class ProductionCompany(
        @field:SerializedName("logo_path")
        val logoPath: Any? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("origin_country")
        val originCountry: String? = null
)
