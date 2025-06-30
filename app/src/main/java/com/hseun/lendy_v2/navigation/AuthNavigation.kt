package com.hseun.lendy_v2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hseun.lendy_v2.auth.identification.IdentificationScreen
import com.hseun.lendy_v2.auth.login.LoginScreen
import com.hseun.lendy_v2.auth.signup.SignUpScreen
import com.hseun.lendy_v2.auth.splash.SplashScreen

@Composable
fun AuthNavigation(
    navController: NavHostController,
    navToMain: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.SPLASH
    ) {
        composable(NavigationRoutes.SPLASH) {
            SplashScreen(
                navToMain = navToMain,
                navToLogin = {
                    navController.navigate(NavigationRoutes.LOGIN) {
                        popUpTo(NavigationRoutes.SPLASH) { inclusive = true }
                    }
                }
            )
        }
        composable(NavigationRoutes.SIGN_UP) {
            SignUpScreen(
                navToIdentification = {
                    navController.navigate(NavigationRoutes.IDENTIFICATION)
                },
                navToLogin = {
                    navController.navigate(NavigationRoutes.LOGIN) {
                        popUpTo(NavigationRoutes.SIGN_UP) { inclusive = true }
                    }
                }
            )
        }
        composable(NavigationRoutes.IDENTIFICATION) {
            IdentificationScreen(
                navigationGoBack = {
                    navController.popBackStack()
                },
                navToMain = navToMain
            )
        }
        composable(NavigationRoutes.LOGIN) {
            LoginScreen(
                navToMain = navToMain,
                navToSignUp = {
                    navController.navigate(NavigationRoutes.SIGN_UP) {
                        popUpTo(NavigationRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
    }
}