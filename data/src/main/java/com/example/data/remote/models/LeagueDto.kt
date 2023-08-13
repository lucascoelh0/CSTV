package com.example.data.remote.models

import com.google.gson.annotations.SerializedName

data class LeagueDto(
    val id: Int? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    val name: String? = null,
)
