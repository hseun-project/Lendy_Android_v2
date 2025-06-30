package com.hseun.lendy_v2.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hseun.lendy_v2.main.home.viewmodel.HomeViewModel
import com.hseun.lendy_v2.network.model.loan.LentListItemData
import com.hseun.lendy_v2.network.model.loan.RequestListItemData
import com.hseun.lendy_v2.network.model.repay.RepayListItemData
import com.hseun.lendy_v2.ui.LendyButton
import com.hseun.lendy_v2.ui.LoadingView
import com.hseun.lendy_v2.ui.list.LentListItem
import com.hseun.lendy_v2.ui.list.RepayListItem
import com.hseun.lendy_v2.ui.list.RequestListItem
import com.hseun.lendy_v2.ui.theme.BackgroundColor
import com.hseun.lendy_v2.ui.theme.Gray200
import com.hseun.lendy_v2.ui.theme.Gray600
import com.hseun.lendy_v2.ui.theme.LendyFontStyle
import com.hseun.lendy_v2.ui.theme.Main300
import com.hseun.lendy_v2.ui.theme.Main50
import com.hseun.lendy_v2.ui.theme.White

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
    navToApplyLoan: () -> Unit
) {
    val name = viewModel.name
    val creditScore = viewModel.creditScore
    val requestList = viewModel.requestList
    val repayList = viewModel.repayList
    val lentList = viewModel.lentList

    val isLoading = viewModel.isLoading

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.getInfo()
    }

    if (isLoading) {
        LoadingView(text = "정보를 불러오는 중입니다")
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .verticalScroll(scrollState)
        ) {
            MyInfo(
                name = name,
                creditScore = creditScore,
                navToApplyLoan = navToApplyLoan
            )
            if (requestList.isNotEmpty()) {
                RequestList(dataList = requestList, navController = navController)
            }
            if (repayList.isNotEmpty()) {
                RepayList(dataList = repayList, navController = navController)
            }
            if (lentList.isNotEmpty()) {
                LentList(dataList = lentList, navController = navController)
            }
        }
    }
}

@Composable
private fun MyInfo(
    modifier: Modifier = Modifier,
    name: String,
    creditScore: Int,
    navToApplyLoan: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(White)
            .padding(
                start = 30.dp,
                end = 30.dp,
                top = 20.dp,
                bottom = 20.dp
            )
    ) {
        Text(
            text = "안녕하세요",
            style = LendyFontStyle.medium12,
            color = Gray600
        )
        Text(
            modifier = modifier
                .padding(
                    top = 2.dp,
                    bottom = 8.dp
                ),
            text = "${name}님",
            style = LendyFontStyle.semi20
        )
        MyCreditScore(creditScore = creditScore)
        LendyButton(
            enabled = true,
            text = "대출 신청",
            onClick = navToApplyLoan
        )
    }
}

@Composable
private fun MyCreditScore(
    modifier: Modifier = Modifier,
    creditScore: Int
) {
    Column(
        modifier = modifier
            .padding(
                bottom = 20.dp
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = Main300,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 12.dp
            )
    ) {
        Text(
            text = "나의 신용점수",
            style = LendyFontStyle.semi13,
            color = White
        )
        Row(
            modifier = modifier
                .padding(
                    top = 6.dp,
                    bottom = 4.dp
                )
        ) {
            Text(
                text = "$creditScore",
                style = LendyFontStyle.semi24,
                color = White
            )
            Text(
                text = " / 1000",
                style = LendyFontStyle.medium14,
                color = Gray200
            )
        }
        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .height(10.dp),
            progress = { creditScore.toFloat() / 1000 },
            trackColor = Main50,
            color = White,
            strokeCap = StrokeCap.Round
        )
    }
}

@Composable
fun RequestList(
    dataList: List<RequestListItemData>,
    navController: NavHostController
) {
    ListBased(
        title = "대출 요청",
        dataList = dataList,
        navController = navController,
        routerBuilder = { data -> "requestDetail/${data.id}" },
        listItemComponent = { data, onClick ->
            RequestListItem(data = data, onButtonClick = onClick)
        }
    )
}

@Composable
fun RepayList(
    dataList: List<RepayListItemData>,
    navController: NavHostController
) {
    ListBased(
        title = "대출 상환 현황",
        dataList = dataList,
        navController = navController,
        routerBuilder = { data -> "myRepayDetail/${data.id}" },
        listItemComponent = { data, onClick ->
            RepayListItem(data = data, onClick = onClick)
        }
    )
}

@Composable
fun LentList(
    dataList: List<LentListItemData>,
    navController: NavHostController
) {
    ListBased(
        title = "대출 현황",
        dataList = dataList,
        navController = navController,
        routerBuilder = { data -> "lentRepayDetail/${data.id}" },
        listItemComponent = { data, onClick ->
            LentListItem(data = data, onClick = onClick)
        }
    )
}

@Composable
private fun <T> ListBased(
    modifier: Modifier = Modifier,
    title: String,
    dataList: List<T>,
    navController: NavHostController,
    routerBuilder: (T) -> String,
    listItemComponent: @Composable (T, () -> Unit) -> Unit
) {
    Column(
        modifier = modifier
            .padding(
                start = 30.dp,
                end = 30.dp,
                bottom = 30.dp
            )
            .fillMaxWidth()
    ) {
        Spacer(modifier = modifier.height(40.dp))
        Text(
            text = title,
            style = LendyFontStyle.bold18
        )
        Column(
            modifier = modifier
                .padding(
                    top = 8.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            for (data in dataList) {
                listItemComponent(data) { navController.navigate(routerBuilder(data)) }
            }
        }
    }
}