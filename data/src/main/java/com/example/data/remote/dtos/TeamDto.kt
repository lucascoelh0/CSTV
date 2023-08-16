package com.example.data.remote.dtos

import com.example.domain.models.TeamModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamDto(
    val id: Int? = null,
    @Json(name = "image_url")
    val imageUrl: String? = null,
    val name: String? = null,
    val slug: String? = null,
)

fun TeamDto.toModel() = TeamModel(
    id = id ?: -1,
    imageUrl = imageUrl.orEmpty(),
    name = name.orEmpty(),
    slug = slug.orEmpty(),
)
