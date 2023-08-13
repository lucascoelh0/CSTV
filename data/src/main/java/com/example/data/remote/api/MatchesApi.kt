package com.example.data.remote.api

import com.example.data.remote.models.common.GenericErrorResponse
import com.example.data.remote.models.MatchDto
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET

fun interface MatchesApi {
    @GET("matches")
    suspend fun getAllMatches(): NetworkResponse<List<MatchDto>, GenericErrorResponse>
}
