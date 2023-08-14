package com.example.domain.models

import java.io.Serializable

data class LeagueModel(
    val id: Int,
    val imageUrl: String,
    val name: String,
) : Serializable
