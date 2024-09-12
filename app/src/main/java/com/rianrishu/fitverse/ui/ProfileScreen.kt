package com.rianrishu.fitverse.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_normal)),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Row {
                TextField(
                    value = stringResource(id = R.string.steps_label),
                    onValueChange = { },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(id = R.string.steps_label)) },
                    textStyle = TextStyle(textAlign = TextAlign.Center),
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
                    value = stringResource(id = R.string.distance_label),
                    onValueChange = { },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(id = R.string.distance_label)) },
                    textStyle = TextStyle(textAlign = TextAlign.Center),
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
                    value = stringResource(id = R.string.active_mins_label),
                    onValueChange = { },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(id = R.string.active_mins_label)) },
                    textStyle = TextStyle(textAlign = TextAlign.Center),
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
                    value = stringResource(id = R.string.sleep_hours_label),
                    onValueChange = { },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(id = R.string.sleep_hours_label)) },
                    textStyle = TextStyle(textAlign = TextAlign.Center),
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
            Text(
                text = stringResource(id = R.string.about_you_label),
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_normal)),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Row {
                TextField(
                    value = stringResource(id = R.string.gender_label),
                    onValueChange = { },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(id = R.string.gender_label)) },
                    textStyle = TextStyle(textAlign = TextAlign.Center),
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
                    value = stringResource(id = R.string.birthday_label),
                    onValueChange = { },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(id = R.string.birthday_label)) },
                    textStyle = TextStyle(textAlign = TextAlign.Center),
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
                    value = stringResource(id = R.string.weight_label),
                    onValueChange = { },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(id = R.string.weight_label)) },
                    textStyle = TextStyle(textAlign = TextAlign.Center),
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
                    value = stringResource(id = R.string.height_label),
                    onValueChange = { },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(id = R.string.height_label)) },
                    textStyle = TextStyle(textAlign = TextAlign.Center),
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