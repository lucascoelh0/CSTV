package com.example.data.remote.api

import com.example.data.remote.models.common.GenericErrorResponse
import com.example.data.remote.models.MatchDto
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchesApi {
    @GET("matches")
    suspend fun getAllMatches(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 50,
        @Query("sort") sort: String = "-status,begin_at",
        @Query("range[begin_at]") range: String,
    ): NetworkResponse<List<MatchDto>, GenericErrorResponse>
}
