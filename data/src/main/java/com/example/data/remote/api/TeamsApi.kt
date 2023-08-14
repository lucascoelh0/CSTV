package com.example.data.remote.api

import com.example.data.remote.models.TeamDetailsDto
import com.example.data.remote.models.common.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

fun interface TeamsApi {
    @GET("teams")
    suspend fun getTeamDetails(
        @Query("filter[id]") id: String,
    ): NetworkResponse<List<TeamDetailsDto>, GenericErrorResponse>
}
