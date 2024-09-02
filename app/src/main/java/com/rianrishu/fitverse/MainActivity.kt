package com.rianrishu.fitverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.rianrishu.fitverse.utils.makeStatusBarTransparent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeStatusBarTransparent()
        FirebaseApp.initializeApp(this)
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        FitVerseNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}