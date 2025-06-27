package com.hseun.lendy_v2.utils

private val MAIL_REGEX = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\$".toRegex()
private val PASSWORD_REGEX = "^[a-zA-Z0-9~!@#$%^&*()_+=?.-]{8,20}$".toRegex()

enum class InputRegexType {
    MAIL,
    PASSWORD
}

fun checkInputRegex(
    type: InputRegexType,
    input: String
): Boolean {
    return when (type) {
        InputRegexType.MAIL -> MAIL_REGEX.matches(input)
        InputRegexType.PASSWORD -> PASSWORD_REGEX.matches(input)
    }
}