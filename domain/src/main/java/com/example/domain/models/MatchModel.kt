package com.example.domain.models

data class MatchModel(
    val id: Int,
    val league: LeagueModel,
    val serie: SerieModel,
    val opponents: List<OpponentModel>,
    val scheduledAt: String,
    val status: MatchStatus,
)

fun List<MatchModel>.sortByScheduledDate() = filter {
    it.status != MatchStatus.UNKNOWN
}.sortedBy {
    it.scheduledAt
}
