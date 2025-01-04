package com.landgorilla.data.remote.model.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDto(
    @field:Json(name = "original_title")
    val name: String,
    @field:Json(name = "poster_path") val url: String,
)
