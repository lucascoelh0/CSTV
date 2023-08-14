package com.example.domain.models

import java.io.Serializable

data class MatchModel(
    val id: Int,
    val league: LeagueModel,
    val serie: SerieModel,
    val opponents: List<OpponentModel>,
    val scheduledAt: String,
    val status: MatchStatus,
) : Serializable

fun List<MatchModel>.sortByScheduledDate() = filter {
    it.status != MatchStatus.UNKNOWN
}.sortedBy {
    it.scheduledAt
}
