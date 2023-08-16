package com.lucascoelho.cstv.features.matches.ui.common

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
import com.lucascoelho.core.constants.EMPTY
import com.lucascoelho.core.utils.TimeUtils
import com.lucascoelho.domain.models.MatchStatus
import com.lucascoelho.cstv.R
import com.lucascoelho.cstv.theme.Gray20
import com.lucascoelho.cstv.theme.Red500

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
