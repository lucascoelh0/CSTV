package com.example.data.remote.models

import com.google.gson.annotations.SerializedName

data class TeamDetailsDto(
    @SerializedName("image_url")
    val imageUrl: String? = null,
    val name: String? = null,
    val players: List<PlayerDto>? = null,
)

fun TeamDetailsDto.toModel() = com.example.domain.models.TeamDetailsModel(
    imageUrl = imageUrl.orEmpty(),
    name = name.orEmpty(),
    players = players?.toModel() ?: emptyList(),
)

fun List<TeamDetailsDto>.toModel() = map { it.toModel() }
