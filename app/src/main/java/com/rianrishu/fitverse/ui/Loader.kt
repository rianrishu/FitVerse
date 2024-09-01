package com.rianrishu.fitverse.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieConstants

@Preview
@Composable
fun Loader() {

    LottieAnimation(
        composition = composition,
        isPlaying = true,
        iterations = LottieConstants.IterateForever
    )
}