package com.example.data.remote.models

import com.example.domain.models.SerieModel

data class SerieDto(
    val name: String? = null,
)

fun SerieDto.toModel() = SerieModel(
    name = name.orEmpty(),
)
