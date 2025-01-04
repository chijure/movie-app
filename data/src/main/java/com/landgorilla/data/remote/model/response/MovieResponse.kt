package com.landgorilla.data.remote.model.response

import com.landgorilla.data.remote.model.dto.MovieDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse(
    @field:Json(name = "total_pages") val totalPages: Int,
    @field:Json(name = "total_results") val totalResults: Int,
    @field:Json(name = "results") val results: List<MovieDto>,
)
