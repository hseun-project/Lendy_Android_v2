package com.hseun.lendy_v2.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hseun.lendy_v2.network.model.user.ApplyListItemData
import com.hseun.lendy_v2.ui.theme.Gray600
import com.hseun.lendy_v2.ui.theme.LendyFontStyle
import com.hseun.lendy_v2.ui.theme.White
import com.hseun.lendy_v2.ui.utils.dropShadow
import com.hseun.lendy_v2.utils.ApplyLoan
import com.hseun.lendy_v2.utils.ApplyState
import com.hseun.lendy_v2.utils.formatMoney
import java.util.Locale

@Composable
fun ApplyListItem(
    modifier: Modifier = Modifier,
    data: ApplyListItemData
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .dropShadow(
                blur = 4.dp
            )
            .background(
                color = White,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(
                top = 10.dp,
                bottom = 10.dp
            )
        ,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ApplyItemState(
            loanType = data.loanType,
            state = data.state
        )
        if (data.loanType == ApplyLoan.PRIVATE_LOAN) {
            ApplyItemBond(
                name = data.bondName ?: "무명",
                mail = data.bondEmail ?: ""
            )
        }
        ApplyItemText(
            label = "요청 금액",
            value = "${formatMoney(data.money)}원"
        )
        ApplyItemText(
            label = "이자율",
            value = "${data.interest}%"
        )
        ApplyItemText(
            label = "상환기한",
            value = "${data.during}${data.duringType.displayText}"
        )
    }
}

@Composable
private fun ApplyItemState(
    modifier: Modifier = Modifier,
    loanType: ApplyLoan,
    state: ApplyState
) {
    Row(
        modifier = modifier
            .padding(
                start = 12.dp,
                end = 16.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier
                .wrapContentSize()
                .background(loanType.displayBackgroundColor)
                .padding(
                    start = 7.dp,
                    end = 7.dp,
                    top = 3.dp,
                    bottom = 3.dp
                ),
            text = loanType.displayText,
            style = LendyFontStyle.semi11,
            color = loanType.displayTextColor
        )
        Text(
            text = state.displayText,
            style = LendyFontStyle.semi12,
            color = state.displayTextColor
        )
    }
}

@Composable
private fun ApplyItemText(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Row (
        modifier = modifier
            .padding(
                start = 16.dp,
                bottom = 16.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = LendyFontStyle.medium13
        )
        Text(
            text = value,
            style = LendyFontStyle.medium13
        )
    }
}

@Composable
private fun ApplyItemBond(
    modifier: Modifier = Modifier,
    name: String,
    mail: String
) {
    Row (
        modifier = modifier
            .padding(
                start = 16.dp,
                end = 16.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "요청 대상",
            style = LendyFontStyle.medium13
        )
        Row (
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = name,
                style = LendyFontStyle.medium13
            )
            Text(
                text = "(${mail})",
                style = LendyFontStyle.medium10,
                color = Gray600
            )
        }
    }
}