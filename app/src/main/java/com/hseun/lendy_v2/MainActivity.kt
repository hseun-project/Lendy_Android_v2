package com.hseun.lendy_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hseun.lendy_v2.navigation.AuthNavigation
import com.hseun.lendy_v2.navigation.BottomNavigation
import com.hseun.lendy_v2.navigation.MainNavigation
import com.hseun.lendy_v2.navigation.NavigationRoutes
import com.hseun.lendy_v2.ui.LendyTopBar
import com.hseun.lendy_v2.ui.theme.Lendy_v2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lendy_v2Theme {
                LendyScreen()
            }
        }
    }
}

private val bottomNav = listOf(
    NavigationRoutes.OPEN_LOAN,
    NavigationRoutes.HOME,
    NavigationRoutes.MY_PAGE
)

@Composable
fun LendyScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    var isAuthenticated by remember { mutableStateOf(false) }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    if (isAuthenticated) {
        Scaffold(
            topBar = { LendyTopBar() },
            bottomBar = {
                if (currentDestination in bottomNav) {
                    BottomNavigation(navController = navController)
                }
            }
        ) {
            Box(modifier = modifier.padding(it)) {
                MainNavigation(
                    navController = navController,
                    navToAuth = {
                        isAuthenticated = false
                    }
                )
            }
        }
    } else {
        AuthNavigation(
            navController = navController,
            navToMain = {
                isAuthenticated = true
            }
        )
    }
}