package com.rianrishu.fitverse.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rianrishu.fitverse.R
import com.rianrishu.fitverse.utils.CustomDrawerState
import com.rianrishu.fitverse.utils.opposite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    drawerState: CustomDrawerState = CustomDrawerState.Closed,
    onDrawerClick: (CustomDrawerState) -> Unit = {}
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .clickable(enabled = drawerState == CustomDrawerState.Opened) {
                onDrawerClick(CustomDrawerState.Closed)
            },
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.profile_label)) },
                navigationIcon = {
                    IconButton(onClick = { onDrawerClick(drawerState.opposite()) }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu Icon"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = modifier.padding(it)
        ) {
            Text(
                text = stringResource(id = R.string.profile_heading_text),
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_normal))
            )
            Row {
                TextField(
                    value = "signInViewModel.password",
                    onValueChange = { },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(id = R.string.steps_label)) },
                    modifier = modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_normal),
                            top = dimensionResource(id = R.dimen.padding_normal),
                            end = dimensionResource(id = R.dimen.one_space),
                            bottom = dimensionResource(id = R.dimen.padding_normal),
                        )
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.auth_bg_corner_radius)))
                        .background(colorResource(id = R.color.gray2))
                        .weight(1f)
                )
                TextField(
                    value = "signInViewModel.password",
                    onValueChange = { },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(id = R.string.steps_label)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(id = R.dimen.one_space),
                            top = dimensionResource(id = R.dimen.padding_normal),
                            end = dimensionResource(id = R.dimen.padding_normal),
                            bottom = dimensionResource(id = R.dimen.padding_normal),
                        )
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.auth_bg_corner_radius)))
                        .background(colorResource(id = R.color.gray2))
                        .weight(1f)
                )
            }
            Row {
                TextField(
                    value = "signInViewModel.password",
                    onValueChange = { },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(id = R.string.steps_label)) },
                    modifier = modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_normal),
                            top = dimensionResource(id = R.dimen.padding_normal),
                            end = dimensionResource(id = R.dimen.one_space),
                            bottom = dimensionResource(id = R.dimen.padding_normal),
                        )
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.auth_bg_corner_radius)))
                        .background(colorResource(id = R.color.gray2))
                        .weight(1f)
                )
                TextField(
                    value = "signInViewModel.password",
                    onValueChange = { },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(id = R.string.steps_label)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(id = R.dimen.one_space),
                            top = dimensionResource(id = R.dimen.padding_normal),
                            end = dimensionResource(id = R.dimen.padding_normal),
                            bottom = dimensionResource(id = R.dimen.padding_normal),
                        )
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.auth_bg_corner_radius)))
                        .background(colorResource(id = R.color.gray2))
                        .weight(1f)
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}