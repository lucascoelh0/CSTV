package com.luminay.gomatches.features.matches.navigation

import com.example.domain.models.MatchModel
import com.google.gson.Gson
import com.ramcosta.composedestinations.navargs.DestinationsNavTypeSerializer
import com.ramcosta.composedestinations.navargs.NavTypeSerializer

@NavTypeSerializer
class MatchModelSerializer : DestinationsNavTypeSerializer<MatchModel> {
    override fun fromRouteString(routeStr: String): MatchModel = Gson().fromJson(routeStr, MatchModel::class.java)

    override fun toRouteString(value: MatchModel): String = Gson().toJson(value)
}
