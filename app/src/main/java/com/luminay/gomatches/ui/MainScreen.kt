package com.luminay.gomatches.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.luminay.gomatches.features.matches.MatchesScreen
import com.luminay.gomatches.theme.GoMatchesTheme

@ExperimentalMaterial3Api
@Composable
fun MainScreen() {
    GoMatchesTheme {
        MatchesScreen()
    }
}
