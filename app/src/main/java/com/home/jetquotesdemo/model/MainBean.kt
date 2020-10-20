package com.home.jetquotesdemo.model

import com.squareup.moshi.Json

data class MainBean(
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "address")
    val address: String? = null,
    @Json(name = "url")
    val url: String? = null
)