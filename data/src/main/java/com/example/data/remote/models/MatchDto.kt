package com.example.data.remote.models

import com.example.domain.models.MatchModel
import com.example.domain.models.MatchStatus
import com.google.gson.annotations.SerializedName

data class MatchDto(
    val id: Int? = null,
    val league: LeagueDto? = null,
    val serie: SerieDto? = null,
    val opponents: List<OpponentDto>? = null,
    @SerializedName("begin_at")
    val beginAt: String? = null,
    val status: String? = null,
)

fun MatchDto.toModel() = MatchModel(
    id = id ?: -1,
    league = league?.toModel() ?: LeagueDto().toModel(),
    serie = serie?.toModel() ?: SerieDto().toModel(),
    opponents = opponents?.toModel() ?: emptyList(),
    beginAt = beginAt.orEmpty(),
    status = MatchStatus.get(status.orEmpty()),
)

fun List<MatchDto>.toModel() = map { it.toModel() }
