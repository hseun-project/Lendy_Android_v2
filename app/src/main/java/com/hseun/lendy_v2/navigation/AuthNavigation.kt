package com.hseun.lendy_v2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hseun.lendy_v2.splash.NAVIGATION_SPLASH
import com.hseun.lendy_v2.splash.SplashScreen

@Composable
fun AuthNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NAVIGATION_SPLASH
    ) {
        composable(NAVIGATION_SPLASH) {
            SplashScreen(
                navToMain = {
                    // MainNavigation 호출
                },
                navToLogin = {
                    // 로그인 페이지로 이동
                }
            )
        }
    }
}