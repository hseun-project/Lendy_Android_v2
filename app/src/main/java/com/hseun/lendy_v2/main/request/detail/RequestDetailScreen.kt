package com.hseun.lendy_v2.main.request.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hseun.lendy_v2.main.request.detail.viewmodel.RequestDetailViewModel
import com.hseun.lendy_v2.ui.LendyAlertDialog
import com.hseun.lendy_v2.ui.LendyConfirmDialog
import com.hseun.lendy_v2.ui.LoadingView
import com.hseun.lendy_v2.ui.theme.Gray700
import com.hseun.lendy_v2.ui.theme.LendyFontStyle
import com.hseun.lendy_v2.ui.theme.Main
import com.hseun.lendy_v2.ui.theme.White
import com.hseun.lendy_v2.utils.DuringType
import com.hseun.lendy_v2.utils.calculateEndDate
import com.hseun.lendy_v2.utils.calculateInterest
import com.hseun.lendy_v2.utils.formatMoney
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@Composable
fun RequestDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: RequestDetailViewModel = hiltViewModel(),
    requestId: Long,
    navToSend: () -> Unit,
    navToBack: () -> Unit
) {
    val debtName = viewModel.debtName
    val money = viewModel.money
    val duringType = viewModel.duringType
    val during = viewModel.during
    val interest = viewModel.interest

    val isLoading = viewModel.isLoading
    val isConfirmSuccess = viewModel.isConfirmSuccess
    val isCancelSuccess = viewModel.isCancelSuccess

    var isConfirmClick by remember { mutableStateOf(false) }
    var isCancelClick by remember { mutableStateOf(false) }

    BackHandler {
        navToBack()
    }

    LaunchedEffect(Unit) {
        viewModel.getInfo(requestId)
    }

    if (isLoading) {
        LoadingView(text = "정보를 불러오는 중입니다")
    } else {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(White)
                .padding(
                    start = 30.dp,
                    end = 30.dp,
                    top = 40.dp,
                    bottom = 40.dp
                )
        ) {
            Column (
                modifier = modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(40.dp),
                horizontalAlignment = Alignment.Start
            ) {
                DetailTitle(
                    debtName = debtName,
                    money = money
                )
                DetailBody(
                    duringType = duringType,
                    during = during,
                    money = money,
                    interest = interest
                )
            }
            StateChangeButton(
                modifier = modifier.align(Alignment.BottomCenter),
                onConfirmClick = {},
                onCancelClick = {}
            )
        }
    }

    if (isConfirmClick) {
        LendyConfirmDialog(
            title = "대출 요청을 수락하시겠습니까?",
            onClickCancel = {
                isConfirmClick = false
            },
            onClickConfirm = {
                viewModel.onConfirmClick(requestId)
            }
        )
    }
    if (isCancelClick) {
        LendyConfirmDialog(
            title = "대출 요청을 거절하시겠습니까?",
            onClickCancel = {
                isCancelClick = false
            },
            onClickConfirm = {
                viewModel.onCancelClick(requestId)
            }
        )
    }

    if (isConfirmSuccess == true) {
        navToSend()
    } else if (isConfirmSuccess == false) {
        LendyAlertDialog(
            title = "대출 요청 수락에 실패하였습니다",
            onClick = {
                viewModel.changeConfirmSuccess(null)
            }
        )
    }

    if (isCancelSuccess == true) {
        navToBack()
    } else if (isCancelSuccess == false) {
        LendyAlertDialog(
            title = "대출 요청 거절에 실패하였습니다",
            onClick = {
                viewModel.changeCancelSuccess(null)
            }
        )
    }
}

@Composable
private fun DetailTitle(
    debtName: String,
    money: Int
) {
    Column (
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "${debtName}님이",
            style = LendyFontStyle.semi22
        )
        Row (
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "${formatMoney(money)}원",
                style = LendyFontStyle.semi24,
                color = Main
            )
            Text(
                text = "대출을 요청했어요",
                style = LendyFontStyle.semi20
            )
        }
    }
}

@Composable
private fun DetailBodyItem(
    modifier: Modifier = Modifier,
    title: String,
    content: String
) {
    Row (
        horizontalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Text(
            modifier = modifier.width(80.dp),
            text = title,
            style = LendyFontStyle.medium18,
            color = Gray700
        )
        Text(
            text = content,
            style = LendyFontStyle.medium18
        )
    }
}

@Composable
private fun DetailBody(
    duringType: DuringType,
    during: Int,
    money: Int,
    interest: String
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        DetailBodyItem(
            title = "상환 기한", 
            content = "${during}${duringType.displayText}"
        )
        DetailBodyItem(
            title = "상환일",
            content = "${calculateEndDate(Date(), duringType, during)}까지"
        )
        DetailBodyItem(
            title = "대출금",
            content = "${formatMoney(money)}원"
        )
        DetailBodyItem(
            title = "이자율",
            content = "${interest}%"
        )
        DetailBodyItem(
            title = "예상 이자",
            content = "${calculateInterest(duringType, during, money, interest)}원"
        )
    }
}

@Composable
private fun ButtonBased(
    modifier: Modifier = Modifier,
    buttonColor: Color,
    borderColor: Color,
    textColor: Color,
    text: String,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = buttonColor,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(
                top = 11.dp,
                bottom = 11.dp
            )
            .clip(RoundedCornerShape(4.dp))
            .clickable {
                onClick()
            },
        text = text,
        style = LendyFontStyle.semi20,
        textAlign = TextAlign.Center,
        color = textColor
    )
}

@Composable
private fun StateChangeButton(
    modifier: Modifier = Modifier,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Row (
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ButtonBased(
            modifier = modifier.fillMaxWidth(0.48f),
            buttonColor = Main,
            borderColor = Color.Transparent,
            textColor = White,
            text = "승인",
            onClick = onConfirmClick
        )
        ButtonBased(
            modifier = modifier.fillMaxWidth(0.98f),
            buttonColor = White,
            borderColor = Gray700,
            textColor = Gray700,
            text = "거절",
            onClick = onCancelClick
        )
    }
}