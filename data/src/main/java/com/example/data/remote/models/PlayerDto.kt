package com.example.data.remote.models

import com.google.gson.annotations.SerializedName

data class PlayerDto(
    @SerializedName("first_name")
    val firstName: String? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("last_name")
    val lastName: String? = null,
    val name: String? = null,
)
