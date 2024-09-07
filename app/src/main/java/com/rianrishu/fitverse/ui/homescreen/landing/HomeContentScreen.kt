package com.rianrishu.fitverse.ui.homescreen.landing

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import com.rianrishu.fitverse.BuildConfig
import com.rianrishu.fitverse.R
import com.rianrishu.fitverse.data.model.BasicActivity
import com.rianrishu.fitverse.data.model.DataRecord
import com.rianrishu.fitverse.data.model.DataType
import com.rianrishu.fitverse.data.model.SelectedProportion
import com.rianrishu.fitverse.ui.common.BaseAnimatedCircle
import com.rianrishu.fitverse.ui.common.DetailsCard
import com.rianrishu.fitverse.utils.CustomDrawerState
import com.rianrishu.fitverse.utils.HealthConnectUtils
import com.rianrishu.fitverse.utils.convertMilesToMeters
import com.rianrishu.fitverse.utils.convertSleepDurationToMinutes
import com.rianrishu.fitverse.utils.extractProportions
import com.rianrishu.fitverse.utils.opposite
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContentScreen(
    modifier: Modifier = Modifier,
    drawerState: CustomDrawerState = CustomDrawerState.Closed,
    onDrawerClick: (CustomDrawerState) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val interval: Long = 7

    var steps by remember {
        mutableStateOf("0")
    }

    var mins by remember {
        mutableStateOf("0")
    }

    var distance by remember {
        mutableStateOf("0")
    }

    var sleepDuration by remember {
        mutableStateOf("00:00")
    }

    var userActivities: List<BasicActivity> by remember {
        mutableStateOf(arrayListOf())
    }

    var showHealthConnectInstallPopup by remember {
        mutableStateOf(false)
    }

    var selectedProportion by remember {
        mutableStateOf(SelectedProportion(0f, DataType.STEPS))
    }

    val requestPermissions =
        rememberLauncherForActivityResult(PermissionController.createRequestPermissionResultContract()) { granted ->
            if (granted.containsAll(HealthConnectUtils.PERMISSIONS)) {
                // Permissions successfully granted, continuing with reading the data from health connect
                scope.launch {
                    mins = HealthConnectUtils.readMinsForInterval(interval).last().metricValue
                    steps = HealthConnectUtils.readStepsForInterval(interval).last().metricValue
                    distance =
                        HealthConnectUtils.readDistanceForInterval(interval).last().metricValue
                    sleepDuration =
                        HealthConnectUtils.readSleepSessionsForInterval(interval).last().metricValue
                    userActivities =
                        UserActivityData.accumulateData("500", "700", "9", "8:10")
                }
            } else {
                //permissions are rejected, redirect the users to health connect page to give permissions if the permissions page is not appearing
                Toast.makeText(context, "Permissions are rejected", Toast.LENGTH_SHORT).show()
            }
        }

    //checking for the Health connect availability in the device
    LaunchedEffect(key1 = true) {
        when (HealthConnectUtils.checkForHealthConnectInstalled(context)) {
            HealthConnectClient.SDK_UNAVAILABLE -> {
                Toast.makeText(
                    context,
                    "Health Connect is not available in this device",
                    Toast.LENGTH_SHORT
                ).show()
            }

            HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> {
                //asking to install health connect if Health connect is supported but not installed
                showHealthConnectInstallPopup = true
            }

            HealthConnectClient.SDK_AVAILABLE -> {
                //checking for permissions since health connect is available
                if (HealthConnectUtils.checkPermissions()) {
                    //permissions are available , so continue performing actions on Health Connect
                    mins = HealthConnectUtils.readMinsForInterval(interval)[0].metricValue
                    steps = HealthConnectUtils.readStepsForInterval(interval)[0].metricValue
                    distance = HealthConnectUtils.readDistanceForInterval(interval)[0].metricValue
                    sleepDuration =
                        HealthConnectUtils.readSleepSessionsForInterval(interval).last().metricValue
                    userActivities =
                        UserActivityData.accumulateData("500", "700", "9", "8:10")
                } else {
                    //asking for permissions from Health Connect since permissions are not given already
                    requestPermissions.launch(HealthConnectUtils.PERMISSIONS)
                }
            }
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .clickable(enabled = drawerState == CustomDrawerState.Opened) {
                onDrawerClick(CustomDrawerState.Closed)
            },
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
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
            modifier = modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            Box {
                val userActivitiesProportion =
                    userActivities.extractProportions { userActivity ->
                        val formattedMetric: Float = when (userActivity.dataRecord.dataType) {
                            DataType.SLEEP ->
                                convertSleepDurationToMinutes(userActivity.dataRecord.metricValue)

                            DataType.DISTANCE ->
                                convertMilesToMeters(userActivity.dataRecord.metricValue)

                            else ->
                                userActivity.dataRecord.metricValue.toFloat()
                        }
                        SelectedProportion(formattedMetric, userActivity.dataRecord.dataType)
                    }
                val circleColors = userActivities.map { userActivity -> userActivity.color }
                BaseAnimatedCircle(
                    proportions = userActivitiesProportion,
                    colors = circleColors,
                    Modifier
                        .height(300.dp)
                        .align(Alignment.TopCenter)
                        .padding(dimensionResource(id = R.dimen.padding_normal))
                        .fillMaxWidth(),
                    onProportionSelected = {
                        selectedProportion = it
                    }
                )
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        text = selectedProportion.dataType.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = selectedProportion.proportion.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    userActivities.forEach { item ->
                        DetailsCard(
                            modifier = Modifier,
                            color = item.color,
                            metricValue = item.dataRecord.metricValue,
                            dataType = item.dataRecord.dataType,
                            toDatetime = item.dataRecord.toDatetime,
                            fromDatetime = item.dataRecord.fromDatetime
                        )
                    }
                }
            }
            if (showHealthConnectInstallPopup) {
                AlertDialog(
                    onDismissRequest = { showHealthConnectInstallPopup = false },
                    confirmButton = {
                        ClickableText(text = AnnotatedString("Install"),
                            onClick = {
                                showHealthConnectInstallPopup = false
                                val uriString = BuildConfig.HEALTH_CONNECT_PLAY_STORE_URL
                                context.startActivity(
                                    Intent(Intent.ACTION_VIEW).apply {
                                        setPackage("com.android.vending")
                                        data = Uri.parse(uriString)
                                        putExtra("overlay", true)
                                        putExtra("callerId", this.`package`)
                                    }
                                )
                            }
                        )
                    },
                    title = {
                        Text(text = "Alert")
                    },
                    text = {
                        Text(text = "Health Connect is not installed")
                    })
            }
        }
    }
}


//Hardcoded values for testing
object UserActivityData {

    fun accumulateData(
        mins: String,
        steps: String,
        distance: String,
        sleepDuration: String
    ): List<BasicActivity> {
        val today = LocalDate.now()
        val oneWeekBefore = today.minusDays(7)
        val formatter = DateTimeFormatter.ofPattern("d'st' MMMM, yyyy", Locale.ENGLISH)
        return listOf(
            BasicActivity(
                DataRecord(
                    steps,
                    DataType.STEPS,
                    today.format(formatter),
                    oneWeekBefore.format(formatter),
                ),
                Color(0xFF36F03F)
            ),
            BasicActivity(
                DataRecord(
                    mins,
                    DataType.MINS,
                    today.format(formatter),
                    oneWeekBefore.format(formatter),
                ),
                Color(0xFFFF78D2)
            ),
            BasicActivity(
                DataRecord(
                    sleepDuration,
                    DataType.SLEEP,
                    today.format(formatter),
                    oneWeekBefore.format(formatter),
                ),
                Color(0xFF788EFF)
            ),
            BasicActivity(
                DataRecord(
                    distance,
                    DataType.DISTANCE,
                    today.format(formatter),
                    oneWeekBefore.format(formatter),
                ),
                Color(0xFFFFAC12)
            )
        )
    }
}
