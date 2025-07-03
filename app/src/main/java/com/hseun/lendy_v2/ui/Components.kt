package com.hseun.lendy_v2.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hseun.lendy_v2.R
import com.hseun.lendy_v2.ui.theme.Gray400
import com.hseun.lendy_v2.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LendyTopBar(
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    CenterAlignedTopAppBar(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .drawBehind {
                drawLine(
                    color = Gray400,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 0.4.dp.toPx()
                )
            },
        title = {
            Image(
                modifier = modifier
                    .height(36.dp),
                painter = painterResource(id = R.drawable.lendy_logo_header),
                contentDescription = "Lendy Logo in Header",
                contentScale = ContentScale.FillHeight
            )
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = White
        )
    )
}

@Composable
fun AuthLogo(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(80.dp))
    Image(
        modifier = modifier
            .fillMaxWidth(0.6f)
            .wrapContentHeight(),
        painter = painterResource(id = R.drawable.lendy_logo_auth),
        contentDescription = "Lendy Logo",
        contentScale = ContentScale.FillWidth
    )
    Spacer(modifier = modifier.height(52.dp))
}