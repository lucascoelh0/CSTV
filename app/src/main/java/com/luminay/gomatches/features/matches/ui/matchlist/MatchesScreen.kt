package com.luminay.gomatches.features.matches.ui.matchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.models.Status
import com.example.domain.models.MatchModel
import com.example.domain.models.sortByStatusAndBeginAt
import com.luminay.gomatches.R
import com.luminay.gomatches.destinations.MatchDetailsScreenDestination
import com.luminay.gomatches.theme.Purple80
import com.luminay.gomatches.common.ui.messages.ErrorMessage
import com.luminay.gomatches.common.ui.pullrefresh.PullRefreshIndicator
import com.luminay.gomatches.common.ui.pullrefresh.pullRefresh
import com.luminay.gomatches.common.ui.pullrefresh.rememberPullRefreshState
import com.luminay.gomatches.utils.getMatchModelMock
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

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
                        message = stringResource(id = R.string.loading_matches_error),
                        modifier = modifier,
                    )
                }
            }

            else -> {
                ErrorMessage(
                    onRetry = { matchListViewModel.fetchData() },
                    message = stringResource(id = R.string.loading_matches_error),
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
    modifier: Modifier = Modifier,
    matchListViewModel: MatchListViewModel = hiltViewModel(),
) {
    val refreshScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val paginationStatus by matchListViewModel.paginationStatus.collectAsStateWithLifecycle(initialValue = null)
    var isRefreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        isRefreshing = true
        matchListViewModel.fetchData()
    }

    val state = rememberPullRefreshState(isRefreshing, ::refresh)

    LaunchedEffect(matches) {
        snapshotFlow { lazyListState.isScrolledToEnd() }
            .filter { it }
            .collect {
                isRefreshing = false
            }
    }

    Box(
        modifier = modifier
            .pullRefresh(state),
    ) {
        LazyColumn(
            state = lazyListState,
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
                when (paginationStatus?.status) {
                    Status.LOADING -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp),
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = Color.White,
                            )
                        }
                    }

                    Status.ERROR -> {
                        ErrorMessage(
                            onRetry = {
                                matchListViewModel.fetchNextPage()
                            },
                            message = stringResource(id = R.string.loading_matches_error),
                        )
                    }

                    else -> {
//                    Do nothing
                    }
                }
            }
        }

        PullRefreshIndicator(
            isRefreshing,
            state,
            Modifier.align(Alignment.TopCenter),
            backgroundColor = Color.White,
            contentColor = Purple80,
        )
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
