package com.rianrishu.fitverse

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rianrishu.fitverse.ui.gettingstarted.GettingStartedScreen
import com.rianrishu.fitverse.ui.homescreen.landing.HomeScreen
import com.rianrishu.fitverse.ui.signin.SignInScreen
import com.rianrishu.fitverse.ui.signup.SignUpScreen

@Composable
fun FitVerseNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = GettingStarted.route,
        modifier = modifier
    ) {
        composable(route = GettingStarted.route) {
            GettingStartedScreen(
                onClickSignIn = {
                    navController.navigateSingleTopTo(SignIn.route)
                },
                onClickSignUp = {
                    navController.navigateSingleTopTo(SignUp.route)
                }
            )
        }

        composable(route = SignIn.route) {
            SignInScreen(
                onClickSignUp = {
                    navController.navigateSingleTopTo(SignUp.route)
                },
                onSuccessfulSignIn = {
                    navController.navigateSingleTopTo(HomeScreen.route)
                }
            )
        }

        composable(route = SignUp.route) {
            SignUpScreen(
                onClickSignIn = {
                    navController.navigateSingleTopTo(SignIn.route)
                },
                onSuccessfulSignUp = {
                    navController.navigateSingleTopTo(SignIn.route)
                }
            )
        }

        composable(route = HomeScreen.route) {
            HomeScreen()
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
