package com.hseun.lendy_v2.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hseun.lendy_v2.R
import com.hseun.lendy_v2.splash.viewmodel.SplashViewModel
import com.hseun.lendy_v2.ui.theme.Main
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navToMain: () -> Unit,
    navToLogin: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.8f) }

    val isChecked = viewModel.isAutoLoginCheck
    val isSuccess = viewModel.isAutoLoginSuccess

    LaunchedEffect(Unit) {
        val animationSpec = tween<Float>(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )
        launch { alpha.animateTo(1f, animationSpec) }
        launch {
            scale.animateTo(1f, animationSpec)
            viewModel.autoLogin()
        }
    }

    LaunchedEffect(isChecked) {
        if (isSuccess) {
            navToMain()
        } else {
            navToLogin()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Main)
    ) {
        Image(
            modifier = modifier
                .align(Alignment.Center)
                .padding(
                    bottom = 40.dp
                )
                .fillMaxWidth(0.7f)
                .wrapContentHeight(),
            painter = painterResource(id = R.drawable.lendy_logo_full),
            contentDescription = "Lendy Logo",
            contentScale = ContentScale.FillWidth
        )
    }
}