package com.hseun.lendy_v2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hseun.lendy_v2.auth.identification.IdentificationScreen
import com.hseun.lendy_v2.auth.signup.SignUpScreen
import com.hseun.lendy_v2.auth.splash.SplashScreen

@Composable
fun AuthNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.SPLASH
    ) {
        composable(NavigationRoutes.SPLASH) {
            SplashScreen(
                navToMain = {
                    // MainNavigation 호출
                },
                navToLogin = {
                    // 로그인 페이지로 이동
                }
            )
        }
        composable(NavigationRoutes.SIGN_UP) {
            SignUpScreen(
                navToIdentification = {
                    navController.navigate(NavigationRoutes.IDENTIFICATION)
                },
                navToLogin = {
                    // 로그인 페이지로 이동
                }
            )
        }
        composable(NavigationRoutes.IDENTIFICATION) {
            IdentificationScreen(
                navigationGoBack = {
                    navController.popBackStack()
                },
                navToMain = {
                    // MainNavigation 호출
                }
            )
        }
    }
}