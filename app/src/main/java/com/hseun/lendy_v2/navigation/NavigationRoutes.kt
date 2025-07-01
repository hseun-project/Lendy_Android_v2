package com.hseun.lendy_v2.navigation

object NavigationRoutes {
    const val SPLASH = "splash"
    const val SIGN_UP = "signUp"
    const val IDENTIFICATION = "identification"
    const val LOGIN = "login"

    const val OPEN_LOAN = "openLoan"
    const val HOME = "home"
    const val MY_PAGE = "myPage"

    private const val MODIFY_BANK = "modifyBank"
    const val MODIFY_BANK_WITH_ARGS = "$MODIFY_BANK/{bankName}/{bankNumber}"

    const val REQUEST_DETAIL = "requestDetail/{requestId}"
}