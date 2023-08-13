package com.example.data.remote.models

import com.google.gson.annotations.SerializedName

data class TeamDetailsDto(
    @SerializedName("image_url")
    val imageUrl: String,
    val name: String,
    val players: List<PlayerDto>,
)

fun TeamDetailsDto.toModel() = com.example.domain.models.TeamDetailsModel(
    imageUrl = imageUrl,
    name = name,
    players = players.toModel(),
)

fun List<TeamDetailsDto>.toModel() = map { it.toModel() }
