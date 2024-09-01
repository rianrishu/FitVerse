package com.rianrishu.fitverse

interface FitVerseDestination {
    val route: String
}

object GettingStarted: FitVerseDestination {
    override val route = "getting_started"
}

object SignIn: FitVerseDestination {
    override val route = "sign_in"
}

object SignUp: FitVerseDestination {
    override val route = "sign_up"
}