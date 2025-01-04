package com.landgorilla.data.mapper

import com.landgorilla.data.local.entity.MovieEntity
import com.landgorilla.data.remote.model.dto.MovieDto
import com.landgorilla.domain.model.Movie

object MovieMapper {
    fun toDomain(entity: MovieEntity): Movie {
        return Movie(
            page = entity.page,
            name = entity.name,
            url = entity.url
        )
    }

    fun toDomain(dto: MovieDto, page: Int): Movie {
        return Movie(
            page = page,
            name = dto.name,
            url = dto.url
        )
    }

    fun toEntity(movie: Movie): MovieEntity {
        return MovieEntity(
            page = movie.page,
            name = movie.name,
            url = movie.url
        )
    }
}