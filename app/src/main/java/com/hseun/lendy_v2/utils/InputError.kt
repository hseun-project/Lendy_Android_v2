package com.hseun.lendy_v2.utils

import com.hseun.lendy_v2.R

enum class InputErrorType {
    NONE,
    WRONG_MAIL_OR_PW,

    MAIL_REGEX,
    PW_REGEX,
    NOT_SAME_PW,
    OVERLAP_MAIL,

    CODE_TIMEOUT,
    CHANGE_MAIL,
    WRONG_CODE,

    OVER_MAX_PRICE,
    OVER_INTEREST,

    IS_NOT_DIGIT
}

fun inputErrorMessage(errorType: InputErrorType): Int {
    return when(errorType) {
        InputErrorType.NONE -> R.string.error_none
        InputErrorType.WRONG_MAIL_OR_PW -> R.string.error_wrong_mail_or_pw

        InputErrorType.MAIL_REGEX -> R.string.error_mail_regex
        InputErrorType.PW_REGEX -> R.string.error_pw_regex
        InputErrorType.NOT_SAME_PW -> R.string.error_not_same_pw
        InputErrorType.OVERLAP_MAIL -> R.string.error_overlap_mail

        InputErrorType.CODE_TIMEOUT -> R.string.error_code_timeout
        InputErrorType.CHANGE_MAIL -> R.string.error_change_mail
        InputErrorType.WRONG_CODE -> R.string.error_wrong_code

        InputErrorType.OVER_MAX_PRICE -> R.string.error_over_max_price
        InputErrorType.OVER_INTEREST -> R.string.error_over_interest

        InputErrorType.IS_NOT_DIGIT -> R.string.error_is_not_digits
    }
}