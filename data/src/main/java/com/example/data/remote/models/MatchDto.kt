package com.example.data.remote.models

import com.google.gson.annotations.SerializedName

data class MatchDto(
    val id: Int? = null,
    val league: LeagueDto? = null,
    val serie: SerieDto? = null,
    val opponents: List<OpponentDto>? = null,
    @SerializedName("scheduled_at")
    val scheduledAt: String? = null,
    val status: String? = null,
)
