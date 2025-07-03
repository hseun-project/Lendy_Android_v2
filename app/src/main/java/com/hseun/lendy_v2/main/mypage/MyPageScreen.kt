package com.hseun.lendy_v2.main.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hseun.lendy_v2.R
import com.hseun.lendy_v2.main.mypage.viewmodel.MyPageViewModel
import com.hseun.lendy_v2.network.model.user.ApplyListItemData
import com.hseun.lendy_v2.ui.LendyAlertDialog
import com.hseun.lendy_v2.ui.LendyButton
import com.hseun.lendy_v2.ui.LendyConfirmDialog
import com.hseun.lendy_v2.ui.LoadingView
import com.hseun.lendy_v2.ui.list.ApplyListItem
import com.hseun.lendy_v2.ui.theme.BackgroundColor
import com.hseun.lendy_v2.ui.theme.Gray600
import com.hseun.lendy_v2.ui.theme.LendyFontStyle
import com.hseun.lendy_v2.ui.theme.White
import com.hseun.lendy_v2.ui.utils.dropShadow
import com.hseun.lendy_v2.ui.utils.noRippleClickable
import java.util.Locale

@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
    navToAuth: () -> Unit,
    navToModifyBank: () -> Unit
) {
    val email = viewModel.email
    val name = viewModel.name
    val creditScore = viewModel.creditScore
    val bankName = viewModel.bankName
    val bankNumber = viewModel.bankNumber
    val money = viewModel.money
    val applyList = viewModel.applyList

    val isLoading = viewModel.isLoading
    var isLogoutClick by remember { mutableStateOf(false) }
    val isLogoutSuccess = viewModel.isLogoutSuccess

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.getInfo()
    }

    if (isLoading) {
        LoadingView(text = "정보를 불러오는 중입니다")
    } else {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(BackgroundColor)
                .padding(
                    start = 30.dp,
                    end = 30.dp
                )
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = modifier.height(30.dp))
            MyInfo(
                name = name,
                email = email,
                score = creditScore
            )
            Spacer(modifier = modifier.height(20.dp))
            BankInfo(
                bankName = bankName,
                bankNumber = bankNumber,
                money = money,
                navToModifyBank = navToModifyBank
            )
            Spacer(modifier = modifier.height(20.dp))
            ApplyLoanList(applyLoanList = applyList)
            Spacer(modifier = modifier.height(40.dp))
            LendyButton(
                enabled = true,
                text = "로그아웃",
                onClick = {
                    isLogoutClick = true
                }
            )
        }
    }

    if (isLogoutClick) {
        LendyConfirmDialog(
            title = "로그아웃하시겠습니까?",
            onClickCancel = {
                isLogoutClick = false
            },
            onClickConfirm = {
                viewModel.logout()
                isLogoutClick = false
            }
        )
    }

    if (isLogoutSuccess == true) {
        navToAuth()
    } else if (isLogoutSuccess == false) {
        LendyAlertDialog(
            title = "로그아웃에 실패하였습니다",
            onClick = {
                viewModel.onChangeLogoutSuccess(null)
            }
        )
    }
}

@Composable
private fun MyInfo(
    modifier: Modifier = Modifier,
    name: String,
    email: String,
    score: Int
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .dropShadow()
            .background(
                color = White,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row {
                Text(
                    text = name,
                    style = LendyFontStyle.semi20
                )
                Text(
                    text = "님",
                    style = LendyFontStyle.semi16
                )
            }
            Text(
                text = email,
                style = LendyFontStyle.medium14,
                color = Gray600
            )
        }
        Column {
            Text(
                text = "신용점수",
                style = LendyFontStyle.medium13,
                color = Gray600
            )
            Text(
                text = "${score}점",
                style = LendyFontStyle.semi21
            )
        }
    }
}

@Composable
private fun BankInfo(
    modifier: Modifier = Modifier,
    bankName: String,
    bankNumber: String,
    money: Int,
    navToModifyBank: () -> Unit
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .dropShadow()
            .background(
                color = White,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 16.dp,
                bottom = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$bankNumber ($bankName)",
                style = LendyFontStyle.semi14
            )
            Text(
                modifier = modifier.noRippleClickable { navToModifyBank() },
                text = "수정",
                style = LendyFontStyle.medium14,
                color = Gray600
            )
        }
        Text(
            text = "${String.format(Locale.getDefault(), "%,d", money)}원",
            style = LendyFontStyle.semi20
        )
    }
}

@Composable
fun ApplyLoanList(
    modifier: Modifier = Modifier,
    applyLoanList: List<ApplyListItemData>
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column (
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .dropShadow()
            .background(
                color = White,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 12.dp
            )
    ) {
        Row (
            modifier = modifier
                .fillMaxWidth()
                .noRippleClickable {
                    isExpanded = !isExpanded
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "대출 요청 내역",
                style = LendyFontStyle.semi15
            )
            Image(
                modifier = modifier
                    .width(12.dp)
                    .rotate(if (isExpanded) 180f else 0f),
                painter = painterResource(id = R.drawable.icon_arrow),
                contentDescription = "펼쳐보기"
            )
        }
        if (isExpanded) {
            applyLoanList.forEach { data ->
                ApplyListItem(data = data)
            }
        }
    }
}