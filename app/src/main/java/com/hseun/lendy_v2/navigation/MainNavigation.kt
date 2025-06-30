package com.hseun.lendy_v2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.hseun.lendy_v2.main.bank.ModifyBankScreen
import com.hseun.lendy_v2.main.home.HomeScreen
import com.hseun.lendy_v2.main.mypage.MyPageScreen
import com.hseun.lendy_v2.main.openloan.OpenLoanScreen

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
            OpenLoanScreen(
                navController = navController
            )
        }
        composable(NavigationRoutes.MY_PAGE) {
            MyPageScreen(
                navToAuth = navToAuth,
                navToModifyBank = {
                    // 계좌 수정으로 이동
                }
            )
        }
        composable(
            route = NavigationRoutes.MODIFY_BANK_WITH_ARGS,
            arguments = listOf(
                navArgument("bankName") { type = NavType.StringType },
                navArgument("bankNumber") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val bankName = backStackEntry.arguments?.getString("bankName") ?: ""
            val bankNumber = backStackEntry.arguments?.getString("bankNumber") ?: ""

            ModifyBankScreen(
                bankName = bankName,
                bankNumber = bankNumber,
                navToGoBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}