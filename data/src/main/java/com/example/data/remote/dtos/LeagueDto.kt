package com.example.data.remote.dtos

import com.example.domain.models.LeagueModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LeagueDto(
    val id: Int? = null,
    @Json(name = "image_url")
    val imageUrl: String? = null,
    val name: String? = null,
)

fun LeagueDto.toModel() = LeagueModel(
    id = id ?: -1,
    imageUrl = imageUrl.orEmpty(),
    name = name.orEmpty(),
)
