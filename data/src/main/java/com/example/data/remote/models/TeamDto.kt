package com.example.data.remote.models

import com.example.domain.models.TeamModel
import com.google.gson.annotations.SerializedName

data class TeamDto(
    val id: Int? = null,
    @SerializedName("image_url")
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
