package com.hseun.lendy_v2.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hseun.lendy_v2.ui.theme.Gray300
import com.hseun.lendy_v2.ui.theme.LendyFontStyle
import com.hseun.lendy_v2.ui.theme.Main
import com.hseun.lendy_v2.ui.theme.White
import com.hseun.lendy_v2.ui.utils.noRippleClickable
import com.hseun.lendy_v2.ui.utils.singleClickEvent

@Composable
fun LendyButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    text: String,
    loading: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(4.dp),
        onClick = onClick,
        colors = ButtonColors(
            disabledContentColor = White,
            disabledContainerColor = Gray300,
            contentColor = White,
            containerColor = Main
        ),
        enabled = enabled
    ) {
        if (loading) {
            CircularProgressIndicator()
        } else {
            Text(
                text = text,
                style = LendyFontStyle.semi20,
                color = White
            )
        }
    }
}

@Composable
fun InputTextButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    buttonText: String,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier
            .wrapContentSize()
            .border(
                width = 1.dp,
                color = if (enabled) Main else Gray300,
                shape = CircleShape
            )
            .padding(
                top = 4.dp,
                bottom = 4.dp,
                start = 12.dp,
                end = 12.dp
            )
            .noRippleClickable {
                onClick()
            },
        text = buttonText,
        style = LendyFontStyle.medium12,
        color = if (enabled) Main else Gray300,
        textAlign = TextAlign.Center
    )
}

@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    loading: Boolean = false,
    buttonText: String,
    isNotText: String,
    moveToWhereText: String,
    onButtonClick: () -> Unit,
    onTextClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(
                bottom = 70.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LendyButton(
            enabled = enabled,
            text = buttonText,
            onClick = onButtonClick,
            loading = loading
        )
        Row(
            modifier = modifier
                .padding(
                    top = 16.dp
                )
        ) {
            Text(
                text = isNotText,
                style = LendyFontStyle.medium15
            )
            Text(
                modifier = modifier
                    .padding(
                        start = 6.dp
                    )
                    .noRippleClickable {
                        onTextClick()
                    },
                text = moveToWhereText,
                style = LendyFontStyle.bold15,
                color = Main
            )
        }
    }
}

@Composable
fun LendyLoanListButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    singleClickEvent { singleEvent ->
        Button(
            modifier = modifier
                .fillMaxWidth()
                .height(42.dp),
            shape = RoundedCornerShape(4.dp),
            onClick = {
                singleEvent.event {
                    onClick()
                }
            },
            colors = ButtonColors(
                containerColor = Main,
                contentColor = White,
                disabledContainerColor = Gray300,
                disabledContentColor = White
            )
        ) {
            Text(
                text = text,
                style = LendyFontStyle.semi18,
                color = White
            )
        }
    }
}