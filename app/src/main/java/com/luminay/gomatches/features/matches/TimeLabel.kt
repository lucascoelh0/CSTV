package com.luminay.gomatches.features.matches

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.constants.EMPTY
import com.example.core.utils.TimeUtils
import com.example.domain.models.MatchStatus
import com.luminay.gomatches.R
import com.luminay.gomatches.theme.Gray20
import com.luminay.gomatches.theme.Red500

@Composable
fun TimeLabel(
    text: String,
    modifier: Modifier = Modifier,
    matchStatus: MatchStatus,
    color: Color = Gray20,
) {
    Box(
        modifier = modifier
            .background(
                if (matchStatus == MatchStatus.RUNNING) Red500 else color,
                shape = RoundedCornerShape(
                    topEnd = 16.dp,
                    bottomStart = 16.dp,
                )
            )
            .padding(8.dp),
    ) {
        Text(
            text = when (matchStatus) {
                MatchStatus.RUNNING -> stringResource(id = R.string.now)
                MatchStatus.NOT_STARTED -> TimeUtils.formatBeginDate(text)
                else -> stringResource(id = R.string.finished)
            },
            modifier = Modifier,
            fontSize = 8.sp,
            color = Color.White,
        )
    }
}

@Preview
@Composable
private fun TopRightLabelRunningPreview() {
    TimeLabel(
        text = stringResource(id = R.string.now),
        matchStatus = MatchStatus.RUNNING,
    )
}

@Preview
@Composable
private fun TopRightLabelNotStartedTodayPreview() {
    TimeLabel(
        text = TimeUtils.getTodayDateUtcString(),
        matchStatus = MatchStatus.NOT_STARTED,
    )
}

@Preview
@Composable
private fun TopRightLabelNotStartedThisWeekPreview() {
    TimeLabel(
        text = TimeUtils.getCurrentWeekLastDayUtcString(),
        matchStatus = MatchStatus.NOT_STARTED,
    )
}

@Preview
@Composable
private fun TopRightLabelFinishedPreview() {
    TimeLabel(
        text = EMPTY,
        matchStatus = MatchStatus.FINISHED,
    )
}
