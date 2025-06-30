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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.hseun.lendy_v2.network.model.repay.RepayListItemData
import com.hseun.lendy_v2.ui.theme.Gray100
import com.hseun.lendy_v2.ui.theme.Gray600
import com.hseun.lendy_v2.ui.theme.LendyFontStyle
import com.hseun.lendy_v2.ui.theme.Main
import com.hseun.lendy_v2.ui.theme.White
import com.hseun.lendy_v2.ui.utils.dropShadow
import com.hseun.lendy_v2.ui.utils.noRippleClickable
import com.hseun.lendy_v2.utils.DuringType
import com.hseun.lendy_v2.utils.calculateDueDate
import com.hseun.lendy_v2.utils.calculateEndDate
import java.util.Date

@Composable
fun RepayListItem(
    modifier: Modifier = Modifier,
    data: RepayListItemData,
    onClick: () -> Unit
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
            .noRippleClickable { onClick() }
            .padding(16.dp)
    ) {
        MyRepayItemText(
            money = data.money,
            repayment = data.repayment,
            startDate = data.startDate,
            duringType = data.duringType,
            during = data.during
        )
        Spacer(modifier = modifier.height(8.dp))
        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .height(10.dp),
            progress = { (data.money - data.repayment).toFloat() / data.money },
            trackColor = Gray100,
            color = Main,
            strokeCap = StrokeCap.Round
        )
        Spacer(modifier = modifier.height(6.dp))
        Text(
            text = "상환일: ${calculateEndDate(data.startDate, data.duringType, data.during)}",
            style = LendyFontStyle.medium12,
            color = Gray600
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun MyRepayItemText(
    modifier: Modifier = Modifier,
    money: Int,
    repayment: Int,
    startDate: Date,
    duringType: DuringType,
    during: Int
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier.align(Alignment.TopStart),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = String.format("%,d", money - repayment),
                style = LendyFontStyle.semi18
            )
            Text(
                text = " / ${String.format(" %,d", money)}",
                style = LendyFontStyle.medium14,
                color = Gray600
            )
        }
        Text(
            modifier = modifier.align(Alignment.TopEnd),
            text = calculateDueDate(startDate, duringType, during),
            style = LendyFontStyle.semi14
        )
    }
}