package com.lucascoelho.cstv.features.matches.ui.matchdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lucascoelho.domain.models.MatchModel
import com.lucascoelho.domain.models.leagueSerieName
import com.lucascoelho.cstv.R
import com.lucascoelho.cstv.utils.getMatchModelMock

@Composable
fun TopBar(
    matchModel: MatchModel,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.back),
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onBackPressed()
                },
            tint = Color.White,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = matchModel.leagueSerieName(),
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
private fun TopBarPreview() {
    TopBar(
        matchModel = getMatchModelMock(),
        onBackPressed = {},
    )
}
