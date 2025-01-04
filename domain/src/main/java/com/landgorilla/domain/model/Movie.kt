package com.landgorilla.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Movie(
    var page: Int = 0,
    @field:Json(name = "original_title") val name: String,
    @field:Json(name = "backdrop_path") val url: String,
): Parcelable {
    fun name(): String = name.replaceFirstChar { it.uppercase() }

    fun getImageUrl(): String {
        return getMovieImageUrl(url)
    }

    companion object {
        private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

        fun getMovieImageUrl(url: String) = "$IMAGE_BASE_URL$url"
    }
}
