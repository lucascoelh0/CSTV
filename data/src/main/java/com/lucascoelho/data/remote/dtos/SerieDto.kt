package com.lucascoelho.data.remote.dtos

import com.lucascoelho.domain.models.SerieModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SerieDto(
    val name: String? = null,
)

fun SerieDto.toModel() = SerieModel(
    name = name.orEmpty(),
)
