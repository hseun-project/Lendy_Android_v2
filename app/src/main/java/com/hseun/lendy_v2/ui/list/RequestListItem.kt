package com.hseun.lendy_v2.ui.list

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hseun.lendy_v2.network.model.loan.RequestListItemData
import com.hseun.lendy_v2.ui.LendyLoanListButton
import com.hseun.lendy_v2.ui.theme.Gray600
import com.hseun.lendy_v2.ui.theme.LendyFontStyle
import com.hseun.lendy_v2.ui.theme.White
import com.hseun.lendy_v2.ui.utils.dropShadow
import com.hseun.lendy_v2.utils.DuringType

@Composable
fun RequestListItem(
    modifier: Modifier = Modifier,
    data: RequestListItemData,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .dropShadow()
            .background(
                color = White,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(
                top = 16.dp,
                bottom = 16.dp,
                start = 20.dp,
                end = 20.dp
            )
    ) {
        RequestItemText(
            name = data.debtName,
            creditScore = data.creditScore,
            money = data.money,
            duringType = data.duringType,
            during = data.during
        )
        Spacer(modifier = modifier.height(12.dp))
        LendyLoanListButton(
            text = "요청 조회",
            onClick = onButtonClick
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun RequestItemText(
    modifier: Modifier = Modifier,
    name: String,
    creditScore: Int,
    money: Int,
    duringType: DuringType,
    during: Int
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            Row (
                verticalAlignment = Alignment.Bottom
            ){
                Text(
                    text = name,
                    style = LendyFontStyle.semi20
                )
                Text(
                    modifier = modifier
                        .padding(
                            start = 6.dp,
                            bottom = 2.dp
                        ),
                    text = "신용점수 ${creditScore}점",
                    style = LendyFontStyle.medium14,
                    color = Gray600
                )
            }
            Text(
                modifier = modifier
                    .padding(top = 4.dp),
                text = "상환기한: ${during}${duringType.displayText}",
                style = LendyFontStyle.medium14
            )
        }
        Text(
            modifier = modifier
                .padding(
                    top = 4.dp
                )
                .align(Alignment.TopEnd),
            text = "₩ ${String.format("%,d", money)}",
            style = LendyFontStyle.bold20
        )
    }
}