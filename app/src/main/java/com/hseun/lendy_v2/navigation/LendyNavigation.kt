package com.hseun.lendy_v2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hseun.lendy_v2.auth.identification.IdentificationScreen
import com.hseun.lendy_v2.auth.login.LoginScreen
import com.hseun.lendy_v2.auth.signup.SignUpScreen
import com.hseun.lendy_v2.auth.splash.SplashScreen
import com.hseun.lendy_v2.home.HomeScreen

@Composable
fun LendyNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.SPLASH
    ) {
        composable(NavigationRoutes.SPLASH) {
            SplashScreen(
                navToMain = {
                    navController.navigate(NavigationRoutes.HOME) {
                        popUpTo(NavigationRoutes.SPLASH) { inclusive = true }
                    }
                },
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
                navToMain = {
                    navController.navigate(NavigationRoutes.HOME) {
                        popUpTo(NavigationRoutes.IDENTIFICATION) { inclusive = true }
                    }
                }
            )
        }
        composable(NavigationRoutes.LOGIN) {
            LoginScreen(
                navToMain = {
                    navController.navigate(NavigationRoutes.HOME) {
                        popUpTo(NavigationRoutes.LOGIN) { inclusive = true }
                    }
                },
                navToSignUp = {
                    navController.navigate(NavigationRoutes.SIGN_UP) {
                        popUpTo(NavigationRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(NavigationRoutes.HOME) {
            HomeScreen(
                navController = navController,
                navToApplyLoan = {
                    // 대출 신청 화면으로 이동
                }
            )
        }
        composable(NavigationRoutes.OPEN_LOAN) {
            // 공개 대출
        }
        composable(NavigationRoutes.MY_PAGE) {
            // 마이페이지
        }
    }
}