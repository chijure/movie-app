package com.landgorilla.core_test

import com.landgorilla.domain.model.Movie

object MockUtil {
    fun mockMovie(
        name: String = "Dead Pool",
        url: String = "",
        page: Int = 1
    ): Movie = Movie(
        page = 0,
        name = "name",
        url = "url",
    )

    fun generateMockMovieList(
        count: Int,
        startIndex: Int = 1,
        overrideFirst: (Movie.() -> Movie)? = null
    ): List<Movie> =
        List(count) { index ->
            val actualIndex = startIndex + index
            val defaultMovie = mockMovie(
                name = "movie_$actualIndex",
                url = "https://image.tmdb.org/t/p/w500/$actualIndex/",
                page = (actualIndex - 1) / 20 + 1
            )

            if (index == 0 && overrideFirst != null) {
                overrideFirst(defaultMovie)
            } else {
                defaultMovie
            }
        }
}