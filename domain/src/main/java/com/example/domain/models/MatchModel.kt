package com.example.domain.models

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class MatchModel(
    val id: Int,
    val league: LeagueModel,
    val serie: SerieModel,
    val opponents: List<OpponentModel>,
    val beginAt: String,
    val status: MatchStatus,
) : Serializable

fun List<MatchModel>.sortByStatusAndBeginAt() = filter {
    it.status != MatchStatus.UNKNOWN
}.sortedWith(
    compareBy(
        { it.status.ordinal },
        { it.beginAt },
    )
)

fun MatchModel.leagueSerieName(): String {
    val leagueSerieName = league.name

    return if (serie.name.isNotEmpty()) {
        "$leagueSerieName - ${serie.name}"
    } else {
        leagueSerieName
    }
}
