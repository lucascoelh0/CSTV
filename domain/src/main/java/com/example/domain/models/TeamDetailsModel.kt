package com.example.domain.models

data class TeamDetailsModel(
    val imageUrl: String,
    val name: String,
    val players: List<PlayerModel>,
)
