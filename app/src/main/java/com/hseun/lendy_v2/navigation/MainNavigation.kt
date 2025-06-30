package com.hseun.lendy_v2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hseun.lendy_v2.main.home.HomeScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    navToAuth: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.HOME
    ) {
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