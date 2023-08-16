package com.example.data.remote.dtos

import com.example.domain.models.OpponentModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpponentDto(
    val opponent: TeamDto? = null,
)

fun OpponentDto.toModel() = OpponentModel(
    opponent = opponent?.toModel() ?: TeamDto().toModel(),
)

fun List<OpponentDto>.toModel() = map { it.toModel() }
