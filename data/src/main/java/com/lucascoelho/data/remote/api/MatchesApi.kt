package com.lucascoelho.data.remote.api

import com.lucascoelho.data.remote.dtos.common.GenericErrorResponse
import com.lucascoelho.data.remote.dtos.MatchDto
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchesApi {
    @GET("matches")
    suspend fun getAllMatches(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 50,
        @Query("sort") sort: String = "-status,begin_at",
    ): NetworkResponse<List<MatchDto>, GenericErrorResponse>
}
