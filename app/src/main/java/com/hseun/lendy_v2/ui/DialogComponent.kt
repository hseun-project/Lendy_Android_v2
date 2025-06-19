package com.hseun.lendy_v2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hseun.lendy_v2.R
import com.hseun.lendy_v2.ui.theme.DialogBlue
import com.hseun.lendy_v2.ui.theme.Gray400
import com.hseun.lendy_v2.ui.theme.Gray700
import com.hseun.lendy_v2.ui.theme.LendyFontStyle
import com.hseun.lendy_v2.ui.theme.Red
import com.hseun.lendy_v2.ui.theme.White

@Composable
private fun DialogContent(
    modifier: Modifier = Modifier,
    title: String,
    isDescription: Boolean = false,
    description: String = ""
) {
    Spacer(modifier = modifier.height(48.dp))
    Text(
        text = title,
        style = LendyFontStyle.medium18
    )
    if (isDescription) {
        Spacer(modifier = modifier.height(12.dp))
        Text(
            text = description,
            style = LendyFontStyle.medium12,
            color = Gray700
        )
    }
    Spacer(modifier = modifier.height(36.dp))
}

@Composable
private fun DialogClickItem(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier
            .clickable {
                onClick()
            }
            .padding(
                top = 14.dp,
                bottom = 14.dp
            ),
        text = text,
        style = LendyFontStyle.medium16,
        color = color,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun AlertClick(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    HorizontalDivider(
        thickness = 0.4.dp,
        color = Gray400
    )
    DialogClickItem(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        text = stringResource(id = R.string.confirm),
        color = DialogBlue,
        onClick = onClick
    )
}

@Composable
private fun ConfirmClick(
    modifier: Modifier = Modifier,
    onClickCancel: () -> Unit,
    onClickConfirm: () -> Unit
) {
    HorizontalDivider(
        thickness = 0.4.dp,
        color = Gray400
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(0.dp)
    ) {
        DialogClickItem(
            modifier = modifier
                .weight(1f),
            text = stringResource(id = R.string.cancel),
            color = Red,
            onClick = onClickCancel
        )
        VerticalDivider(
            thickness = 0.4.dp,
            color = Gray400
        )
        DialogClickItem(
            modifier = modifier
                .weight(1f),
            text = stringResource(id = R.string.confirm),
            color = DialogBlue,
            onClick = onClickConfirm
        )
    }
}

@Composable
private fun DialogContainer(
    onClick: () -> Unit,
    body: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onClick,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            shape = RoundedCornerShape(8.dp)
        ) {
            body()
        }
    }
}

@Composable
fun LendyAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    DialogContainer(onClick = onClick) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DialogContent(
                title = title
            )
            AlertClick(
                onClick = onClick
            )
        }
    }
}

@Composable
fun LendyConfirmDialog(
    modifier: Modifier = Modifier,
    title: String,
    isDescription: Boolean = false,
    description: String = "",
    onClickCancel: () -> Unit,
    onClickConfirm: () -> Unit,
) {
    DialogContainer(onClick = onClickCancel) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DialogContent(
                title = title,
                isDescription = isDescription,
                description = description
            )
            ConfirmClick(
                onClickCancel = onClickCancel,
                onClickConfirm = onClickConfirm
            )
        }
    }
}