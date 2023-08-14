package com.example.domain.models

import java.io.Serializable

data class TeamModel(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val slug: String,
) : Serializable
