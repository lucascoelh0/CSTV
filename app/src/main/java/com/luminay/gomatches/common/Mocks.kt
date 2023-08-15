package com.luminay.gomatches.common

import com.example.core.constants.EMPTY
import com.example.core.constants.TBD
import com.example.core.utils.TimeUtils
import com.example.domain.models.LeagueModel
import com.example.domain.models.MatchModel
import com.example.domain.models.MatchStatus
import com.example.domain.models.PlayerModel
import com.example.domain.models.SerieModel
import com.example.domain.models.TeamModel

const val LEAGUE_NAME = "League Name"
const val SERIE_NAME = "Serie Name"
const val PLAYER_FIRST_NAME = "First"
const val PLAYER_SECOND_NAME = "Second"
const val PLAYER_NICKNAME = "Player Nickname"
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
    SERIE_NAME
)

fun getMatchModelMock() = MatchModel(
    id = 0,
    league = getLeagueMock(),
    serie = getSerieMock(),
    opponents = emptyList(),
    scheduledAt = TimeUtils.getTodayDateUtcString(),
    status = MatchStatus.RUNNING,
)

fun getPlayerModelMock() = PlayerModel(
    firstName = PLAYER_FIRST_NAME,
    imageUrl = EMPTY,
    lastName = PLAYER_SECOND_NAME,
    name = PLAYER_NICKNAME,
)
