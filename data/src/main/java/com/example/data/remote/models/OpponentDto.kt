package com.example.data.remote.models

import com.example.domain.models.OpponentModel

data class OpponentDto(
    val opponent: TeamDto? = null,
)

fun OpponentDto.toModel() = OpponentModel(
    opponent = opponent?.toModel() ?: TeamDto().toModel(),
)

fun List<OpponentDto>.toModel() = map { it.toModel() }
