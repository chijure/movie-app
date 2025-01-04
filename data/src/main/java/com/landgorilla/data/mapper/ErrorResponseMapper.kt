package com.landgorilla.data.mapper

import com.landgorilla.data.remote.model.response.MovieErrorResponse
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.mappers.ApiErrorModelMapper
import com.skydoves.sandwich.message
import com.skydoves.sandwich.retrofit.statusCode
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorResponseMapper @Inject constructor() : ApiErrorModelMapper<MovieErrorResponse> {

    override fun map(apiErrorResponse: ApiResponse.Failure.Error): MovieErrorResponse {
        return MovieErrorResponse(apiErrorResponse.statusCode.code, apiErrorResponse.message())
    }
}