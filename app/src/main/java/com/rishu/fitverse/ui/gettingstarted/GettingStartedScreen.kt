package com.rishu.fitverse.ui.gettingstarted

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rishu.fitverse.R

@Preview
@Composable
fun GettingStartedScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(id = R.color.lt_darkpblue)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(colorResource(id = R.color.lt_darkpblue))
        ) {
            Image(
                painter = painterResource(id = R.drawable.getting_started_illustration),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_normal))
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clip(
                    RoundedCornerShape(
                        topStart = dimensionResource(id = R.dimen.auth_bg_corner_radius),
                        topEnd = dimensionResource(id = R.dimen.auth_bg_corner_radius)
                    )
                )
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            Text(
                text = stringResource(id = R.string.welcome_to_label),
                style = MaterialTheme.typography.displaySmall,
                color = colorResource(id = R.color.lt_darkpblue),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    dimensionResource(R.dimen.auth_bg_corner_radius),
                    dimensionResource(R.dimen.auth_bg_corner_radius),
                    dimensionResource(R.dimen.padding_normal),
                    dimensionResource(R.dimen.padding_normal)
                )
            )

            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.displayMedium,
                color = colorResource(id = R.color.lt_yellow),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    dimensionResource(id = R.dimen.auth_bg_corner_radius),
                    dimensionResource(R.dimen.size0dp)
                )
            )

            Text(
                text = stringResource(id = R.string.gettingStarted_body),
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.gray4),
                modifier = Modifier.padding(
                    dimensionResource(id = R.dimen.auth_bg_corner_radius),
                    dimensionResource(R.dimen.padding_normal)
                )
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size60dp)))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(dimensionResource(R.dimen.auth_bg_corner_radius))
                    .border(
                        width = dimensionResource(id = R.dimen.border_width),
                        color = colorResource(id = R.color.lt_darkpblue),
                        shape = RoundedCornerShape(
                            topStart = dimensionResource(R.dimen.auth_bg_corner_radius),
                            topEnd = dimensionResource(R.dimen.auth_bg_corner_radius),
                            bottomStart = dimensionResource(R.dimen.auth_bg_corner_radius),
                            bottomEnd = dimensionResource(R.dimen.auth_bg_corner_radius)
                        )
                    )
            ) {
                Button(
                    onClick = { /* TODO: Sign in action */ },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.lt_darkpblue)
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_in_label),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Button(
                    onClick = { /* TODO: Sign up action */ },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_up_label),
                        color = colorResource(id = R.color.lt_darkpblue),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}