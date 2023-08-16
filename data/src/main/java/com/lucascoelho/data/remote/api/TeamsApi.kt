package com.lucascoelho.data.remote.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.lucascoelho.data.remote.dtos.TeamDetailsDto
import com.lucascoelho.data.remote.dtos.common.GenericErrorResponse
import retrofit2.http.GET
import retrofit2.http.Query

fun interface TeamsApi {
    @GET("teams")
    suspend fun getTeamDetails(
        @Query("filter[id]") id: String,
    ): NetworkResponse<List<TeamDetailsDto>, GenericErrorResponse>
}
