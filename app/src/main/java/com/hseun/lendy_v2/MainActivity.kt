package com.hseun.lendy_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.hseun.lendy_v2.navigation.LendyNavigation
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

@Composable
fun LendyScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    Scaffold (
        topBar = { LendyTopBar() },
        bottomBar = {}
    ) {
        Box(modifier = modifier.padding(it)) {
            LendyNavigation(
                navController = navController
            )
        }
    }
}