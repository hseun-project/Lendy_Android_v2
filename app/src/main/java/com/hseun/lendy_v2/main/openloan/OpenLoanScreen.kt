package com.hseun.lendy_v2.main.openloan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hseun.lendy_v2.main.openloan.viewmodel.OpenLoanViewModel
import com.hseun.lendy_v2.ui.LoadingView
import com.hseun.lendy_v2.ui.list.RequestListItem
import com.hseun.lendy_v2.ui.theme.BackgroundColor

@Composable
fun OpenLoanScreen(
    modifier: Modifier = Modifier,
    viewModel: OpenLoanViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val requestList = viewModel.requestList
    val isLoading = viewModel.isLoading

    LaunchedEffect(Unit) {
        viewModel.getInfo()
    }

    if (isLoading) {
        LoadingView(text = "정보를 불러오는 중입니다")
    } else {
        LazyColumn (
            modifier = modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(
                    start = 30.dp,
                    end = 30.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(requestList) { item ->
                RequestListItem(
                    data = item,
                    onButtonClick = {
                        navController.navigate("requestDetail/${item.id}")
                    }
                )
            }
        }
    }
}