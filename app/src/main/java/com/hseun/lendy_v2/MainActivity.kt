package com.hseun.lendy_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.hseun.lendy_v2.navigation.AuthNavigation
import com.hseun.lendy_v2.ui.LendyTopBar
import com.hseun.lendy_v2.ui.theme.Lendy_v2Theme

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
            AuthNavigation(
                navController = navController
            )
        }
    }
}