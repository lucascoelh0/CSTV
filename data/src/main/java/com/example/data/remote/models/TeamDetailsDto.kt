package com.example.data.remote.models

import com.google.gson.annotations.SerializedName

data class TeamDetailsDto(
    @SerializedName("image_url")
    val imageUrl: String,
    val name: String,
    val players: List<PlayerDto>,
)
