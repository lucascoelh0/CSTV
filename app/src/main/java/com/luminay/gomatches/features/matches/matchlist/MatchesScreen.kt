package com.luminay.gomatches.features.matches.matchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
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
import com.example.core.models.Status
import com.example.domain.models.MatchModel
import com.example.domain.models.sortByStatusAndBeginAt
import com.luminay.gomatches.R
import com.luminay.gomatches.common.getMatchModelMock
import com.luminay.gomatches.destinations.MatchDetailsScreenDestination
import com.luminay.gomatches.theme.Purple80
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filter

@Destination
@Composable
fun MatchesScreen(
    id: Int,
    navigator: DestinationsNavigator,
) {
    Scaffold(
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
                onMatchClick = {
                    navigator.navigate(
                        MatchDetailsScreenDestination(
                            id = it.id,
                            it,
                        )
                    )
                },
            )
        }
    )
}

@Composable
fun MatchesStatus(
    paddingValues: PaddingValues,
    onMatchClick: (MatchModel) -> Unit,
    modifier: Modifier = Modifier,
    matchListViewModel: MatchListViewModel = hiltViewModel(),
) {
    val matches by matchListViewModel.matches.collectAsStateWithLifecycle(initialValue = null)
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
                    color = Color.White,
                )
            }

            Status.SUCCESS -> {
                val matchesList = matches?.data
                matchesList?.let {
                    MatchesList(
                        matches = it.sortByStatusAndBeginAt(),
                        onMatchClick = { matchModel ->
                            if (matchModel.opponents.isNotEmpty()) {
                                onMatchClick(matchModel)
                            }
                        },
                        modifier = modifier,
                    )
                } ?: run {
                    ErrorMessage(
                        onRetry = { matchListViewModel.fetchData() },
                        modifier = modifier,
                    )
                }
            }

            else -> {
                ErrorMessage(
                    onRetry = { matchListViewModel.fetchData() },
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
    matchListViewModel: MatchListViewModel = hiltViewModel(),
) {
    val lazyListState = rememberLazyListState()
    val pagingStatus by matchListViewModel.pagingStatus.collectAsStateWithLifecycle(initialValue = null)

    LazyColumn(
        state = lazyListState,
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

        item {
            when (pagingStatus?.status) {
                Status.LOADING -> {
                    CircularProgressIndicator(
                        modifier = modifier,
                        color = Color.White,
                    )
                }

                Status.ERROR -> {
                    ErrorMessage(onRetry = {
                        matchListViewModel.fetchNextPage()
                    })
                }

                else -> {
//                    Do nothing
                }
            }
        }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.isScrolledToEnd() }
            .filter { it }
            .collect {
                matchListViewModel.fetchNextPage()
            }
    }
}

fun LazyListState.isScrolledToEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

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
            text = stringResource(id = R.string.loading_matches_error),
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
