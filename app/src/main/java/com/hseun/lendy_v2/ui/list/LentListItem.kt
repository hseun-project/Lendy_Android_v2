package com.hseun.lendy_v2.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.hseun.lendy_v2.network.model.loan.LentListItemData
import com.hseun.lendy_v2.ui.theme.Gray100
import com.hseun.lendy_v2.ui.theme.Gray500
import com.hseun.lendy_v2.ui.theme.LendyFontStyle
import com.hseun.lendy_v2.ui.theme.Main
import com.hseun.lendy_v2.ui.theme.White
import com.hseun.lendy_v2.ui.utils.dropShadow
import com.hseun.lendy_v2.ui.utils.noRippleClickable
import com.hseun.lendy_v2.utils.DuringType
import com.hseun.lendy_v2.utils.calculateEndDate
import com.hseun.lendy_v2.utils.formatMoney
import java.util.Date
import java.util.Locale

@Composable
fun LentListItem(
    modifier: Modifier = Modifier,
    data: LentListItemData,
    onClick: () -> Unit
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
            .noRippleClickable { onClick() }
            .padding(
                top = 12.dp,
                bottom = 12.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {
        LentItemText(
            name = data.debtName,
            money = data.money,
            duringType = data.duringType,
            during = data.during,
            startDate = data.startDate
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
    }
}

@Composable
private fun LentItemText(
    modifier: Modifier = Modifier,
    name: String,
    money: Int,
    duringType: DuringType,
    during: Int,
    startDate: Date
) {
    Box (
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column (
            modifier = modifier.align(Alignment.TopStart)
        ) {
            Text(
                text = name,
                style = LendyFontStyle.semi16
            )
            Text(
                modifier = modifier.padding(top = 2.dp),
                text = "상환일: ${calculateEndDate(startDate, duringType, during)}",
                style = LendyFontStyle.medium12,
                color = Gray500
            )
        }
        Text(
            modifier = modifier
                .align(Alignment.CenterEnd),
            text = "₩ ${formatMoney(money)}",
            style = LendyFontStyle.semi16
        )
    }
}