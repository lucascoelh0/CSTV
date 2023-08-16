package com.lucascoelho.cstv

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.lucascoelho.cstv.destinations.MatchesScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@ExperimentalMaterial3Api
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
) {
    navigator.navigate(
        MatchesScreenDestination(
            id = 1,
        ),
    )
}
