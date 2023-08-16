package com.lucascoelho.domain.utils

import com.lucascoelho.core.constants.EMPTY
import com.lucascoelho.core.constants.TBD
import com.lucascoelho.domain.models.TeamModel

fun getEmptyTeamModel(): TeamModel = TeamModel(
    id = -1,
    name = TBD,
    imageUrl = EMPTY,
    slug = EMPTY,
)
