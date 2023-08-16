package com.lucascoelho.data.remote.dtos.common

import com.squareup.moshi.Json

data class GenericErrorResponse(
    @Json(name = "status_message")
    val message: String? = null,
    @Json(name = "status_code")
    val statusCode: Int = 0,
)
