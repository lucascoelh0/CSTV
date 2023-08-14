package com.luminay.gomatches.features.matches.matchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.models.Resource
import com.example.core.models.Status
import com.example.domain.models.MatchModel
import com.example.domain.models.sortByScheduledDate
import com.luminay.gomatches.R
import com.luminay.gomatches.common.getMatchModelMock
import com.luminay.gomatches.theme.Purple80

@Composable
fun MatchesScreen(
    modifier: Modifier = Modifier,
    matchListViewModel: MatchListViewModel = hiltViewModel(),
) {
    val matches by matchListViewModel.matches.collectAsStateWithLifecycle(initialValue = null)

    Scaffold(
        modifier = modifier,
        topBar = {
            Text(
                text = stringResource(id = R.string.matches),
                modifier = Modifier
                    .padding(
                        top = 44.dp,
                        bottom = 24.dp,
                        start = 24.dp,
                    ),
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.W500,
            )
        },
        content = { padding ->
            MatchesStatus(
                paddingValues = padding,
                matches = matches,
                onRetry = { matchListViewModel.fetchData() },
                onMatchClick = { /*TODO*/ },
            )
        }
    )
}

@Composable
fun MatchesStatus(
    paddingValues: PaddingValues,
    matches: Resource<List<MatchModel>>?,
    onRetry: () -> Unit,
    onMatchClick: (MatchModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when (matches?.status) {
            Status.LOADING -> {
                CircularProgressIndicator(
                    modifier = modifier,
                    color = Purple80,
                )
            }

            Status.SUCCESS -> {
                val matchesList = matches.data
                matchesList?.let {
                    MatchesList(
                        matches = it.sortByScheduledDate(),
                        onMatchClick = onMatchClick,
                        modifier = modifier,
                    )
                } ?: run {
                    ErrorMessage(
                        onRetry = onRetry,
                        modifier = modifier,
                    )
                }
            }

            else -> {
                ErrorMessage(
                    onRetry = onRetry,
                    modifier = modifier,
                )
            }
        }
    }
}

@Composable
fun MatchesList(
    matches: List<MatchModel>,
    onMatchClick: (MatchModel) -> Unit,
    modifier: Modifier,
) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier,
        contentPadding = PaddingValues(
            end = 24.dp,
            bottom = 24.dp,
            start = 24.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(items = matches) {
            MatchInfoCard(
                matchModel = it,
                onClick = onMatchClick,
            )
        }
    }
}

@Composable
fun ErrorMessage(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = R.string.loading_error),
            textAlign = TextAlign.Center,
            color = Color.White,
        )

        Button(
            onClick = onRetry,
            modifier = Modifier.padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple80,
            ),
        ) {
            Text(
                text = stringResource(id = R.string.try_again),
                color = Color.White,
            )
        }
    }
}

@Preview
@Composable
private fun MatchesListPreview() {
    MatchesList(
        matches = listOf(
            getMatchModelMock(),
            getMatchModelMock(),
        ),
        onMatchClick = {},
        modifier = Modifier.padding(24.dp),
    )
}

@Preview
@Composable
private fun ErrorMessagePreview() {
    ErrorMessage(onRetry = {})
}
