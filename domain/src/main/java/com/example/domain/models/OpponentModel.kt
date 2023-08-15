package com.example.domain.models

import com.example.domain.utils.getEmptyTeamModel
import java.io.Serializable

data class OpponentModel(
    val opponent: TeamModel,
) : Serializable

fun List<OpponentModel>.getOpponentsInfo(): Pair<TeamModel, TeamModel> {
    return when (size) {
        0 -> {
            Pair(
                getEmptyTeamModel(),
                getEmptyTeamModel(),
            )
        }

        1 -> {
            Pair(
                first().opponent,
                getEmptyTeamModel(),
            )
        }

        else -> {
            Pair(
                first().opponent,
                this[1].opponent,
            )
        }
    }
}
