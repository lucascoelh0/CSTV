package com.example.data.remote.dtos

import com.example.domain.models.TeamDetailsModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamDetailsDto(
    @Json(name = "image_url")
    val imageUrl: String? = null,
    val name: String? = null,
    val players: List<PlayerDto>? = null,
)

fun TeamDetailsDto.toModel() = TeamDetailsModel(
    imageUrl = imageUrl.orEmpty(),
    name = name.orEmpty(),
    players = players?.toModel() ?: emptyList(),
)

fun List<TeamDetailsDto>.toModel() = map { it.toModel() }
