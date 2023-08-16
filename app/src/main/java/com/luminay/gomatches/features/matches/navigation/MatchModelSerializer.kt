package com.luminay.gomatches.features.matches.navigation

import com.example.data.remote.dtos.MatchDto
import com.example.data.remote.dtos.toModel
import com.example.domain.models.MatchModel
import com.ramcosta.composedestinations.navargs.DestinationsNavTypeSerializer
import com.ramcosta.composedestinations.navargs.NavTypeSerializer
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@NavTypeSerializer
class MatchModelSerializer : DestinationsNavTypeSerializer<MatchModel> {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val jsonAdapter = moshi.adapter(MatchModel::class.java)

    override fun fromRouteString(routeStr: String): MatchModel = jsonAdapter.fromJson(routeStr) ?: MatchDto().toModel()

    override fun toRouteString(value: MatchModel): String = jsonAdapter.toJson(value)
}
