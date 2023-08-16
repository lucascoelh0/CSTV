package com.example.data.remote.dtos

import com.example.domain.models.SerieModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SerieDto(
    val name: String? = null,
)

fun SerieDto.toModel() = SerieModel(
    name = name.orEmpty(),
)
