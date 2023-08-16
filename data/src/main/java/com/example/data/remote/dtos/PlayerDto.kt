package com.example.data.remote.dtos

import com.example.domain.models.PlayerModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayerDto(
    @Json(name = "first_name")
    val firstName: String? = null,
    @Json(name = "image_url")
    val imageUrl: String? = null,
    @Json(name = "last_name")
    val lastName: String? = null,
    val name: String? = null,
)

fun PlayerDto.toModel() = PlayerModel(
    firstName = firstName.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    lastName = lastName.orEmpty(),
    name = name.orEmpty(),
)

fun List<PlayerDto>.toModel() = map { it.toModel() }
