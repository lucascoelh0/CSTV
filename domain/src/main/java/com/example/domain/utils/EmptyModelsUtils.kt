package com.example.domain.utils

import com.example.core.constants.EMPTY
import com.example.core.constants.TBD
import com.example.domain.models.TeamModel

fun getEmptyTeamModel(): TeamModel = TeamModel(
    id = -1,
    name = TBD,
    imageUrl = EMPTY,
    slug = EMPTY,
)
