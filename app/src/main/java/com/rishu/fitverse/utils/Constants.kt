package com.rishu.fitverse.utils

import com.rishu.fitverse.R

enum class ErrorType(val title: Int, val message: Int, val image: Int) {
    NO_INTERNET(R.string.no_internet_title, R.string.no_internet_message, R.drawable.no_internet),
    UNKNOWN(R.string.unknown_error_title, R.string.unknown_error_message, R.drawable.no_internet)
}