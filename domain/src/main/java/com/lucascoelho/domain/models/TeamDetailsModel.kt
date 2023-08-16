package com.lucascoelho.domain.models

data class TeamDetailsModel(
    val imageUrl: String,
    val name: String,
    val players: List<PlayerModel>,
)
