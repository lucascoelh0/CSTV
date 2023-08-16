package com.example.data.remote.dtos

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

fun PlayerDto.toModel() = com.example.domain.models.PlayerModel(
    firstName = firstName.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    lastName = lastName.orEmpty(),
    name = name.orEmpty(),
)

fun List<PlayerDto>.toModel() = map { it.toModel() }
