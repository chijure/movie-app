package com.landgorilla.data.remote.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieErrorResponse(
    val code: Int? = null,
    val message: String? = null,
)
