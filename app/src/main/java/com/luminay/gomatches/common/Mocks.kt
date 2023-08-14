package com.luminay.gomatches.common

import com.example.core.constants.EMPTY
import com.example.core.constants.TBD
import com.example.domain.models.LeagueModel
import com.example.domain.models.MatchModel
import com.example.domain.models.MatchStatus
import com.example.domain.models.SerieModel
import com.example.domain.models.TeamModel

const val LEAGUE_NAME = "League Name"
const val URL = "https://cdn.pandascore.co/images/team/image/133512/190px__72_c.png"

fun getTeamMock() = TeamModel(
    id = -1,
    name = TBD,
    imageUrl = EMPTY,
    slug = EMPTY,
)

fun getLeagueMock() = LeagueModel(
    0,
    URL,
    LEAGUE_NAME,
)

fun getSerieMock() = SerieModel(
    EMPTY
)

fun getMatchModelMock() = MatchModel(
    id = 0,
    league = getLeagueMock(),
    serie = getSerieMock(),
    opponents = emptyList(),
    scheduledAt = EMPTY,
    status = MatchStatus.RUNNING,
)
