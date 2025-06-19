package com.hseun.lendy_v2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hseun.lendy_v2.R
import com.hseun.lendy_v2.ui.theme.Gray400
import com.hseun.lendy_v2.ui.theme.Gray500
import com.hseun.lendy_v2.ui.theme.LendyFontStyle
import com.hseun.lendy_v2.ui.theme.Main
import com.hseun.lendy_v2.ui.theme.Red
import com.hseun.lendy_v2.ui.theme.White
import com.hseun.lendy_v2.ui.utils.noRippleClickable
import com.hseun.lendy_v2.utils.InputErrorType
import com.hseun.lendy_v2.utils.inputErrorMessage

@Composable
private fun LendyBasicTextField(
    modifier: Modifier = Modifier,
    input: String,
    hint: String,
    imeAction: ImeAction,
    textStyle: TextStyle,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: (@Composable (() -> Unit))? = null,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .drawBehind {
                drawLine(
                    color = if (isFocused) Main else Gray500,
                    strokeWidth = 1.dp.toPx(),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height)
                )
            }
            .padding(
                start = 4.dp,
                end = 4.dp,
                top = 12.dp,
                bottom = 12.dp
            )
            .onFocusChanged { isFocused = it.isFocused }
    ) {
        BasicTextField(
            modifier = modifier
                .align(Alignment.CenterStart)
                .fillMaxWidth()
                .padding(
                    end = if (trailingIcon != null) 24.dp else 0.dp
                ),
            value = input,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus(true) },
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            visualTransformation = visualTransformation,
            textStyle = textStyle,
            cursorBrush = SolidColor(Main),
            decorationBox = { innerTextField ->
                if (input.isEmpty()) {
                    Text(
                        text = hint,
                        style = textStyle,
                        color = Gray400
                    )
                }
                innerTextField()
            }
        )
        trailingIcon?.let {
            Box(modifier = modifier.align(Alignment.CenterEnd)) { it() }
        }
    }
}

@Composable
private fun LendyTextField(
    modifier: Modifier = Modifier,
    input: String,
    hint: String,
    imeAction: ImeAction,
    onValueChange: (String) -> Unit
) {
    LendyBasicTextField(
        modifier = modifier,
        input = input,
        hint = hint,
        imeAction = imeAction,
        textStyle = LendyFontStyle.medium15,
        keyboardType = KeyboardType.Text,
        onValueChange = onValueChange
    )
}

@Composable
private fun LendyPasswordTextField(
    modifier: Modifier = Modifier,
    input: String,
    hint: String,
    imeAction: ImeAction,
    onValueChange: (String) -> Unit
) {
    var isShowPassword by remember { mutableStateOf(false) }

    LendyBasicTextField(
        modifier = modifier,
        input = input,
        hint = hint,
        imeAction = imeAction,
        textStyle = LendyFontStyle.medium15,
        keyboardType = KeyboardType.Password,
        onValueChange = onValueChange,
        visualTransformation = if (isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            Icon(
                modifier = modifier
                    .padding(4.dp)
                    .size(20.dp)
                    .noRippleClickable {
                        isShowPassword = !isShowPassword
                    },
                painter = painterResource(id = if (isShowPassword) R.drawable.icon_password_show else R.drawable.icon_password_hide),
                contentDescription = if (isShowPassword) "Hide password Icon" else "Show password Icon"
            )
        }
    )
}

@Composable
private fun LendyNumberTextField(
    modifier: Modifier = Modifier,
    input: String,
    imeAction: ImeAction,
    onValueChange: (String) -> Unit
) {
    LendyBasicTextField(
        modifier = modifier,
        input = input,
        hint = "0",
        imeAction = imeAction,
        textStyle = LendyFontStyle.medium17,
        keyboardType = KeyboardType.Decimal,
        onValueChange = onValueChange
    )
}

@Composable
private fun LendyMailTextField(
    modifier: Modifier = Modifier,
    input: String,
    imeAction: ImeAction,
    enabled: Boolean,
    onButtonClick: () -> Unit,
    onValueChange: (String) -> Unit
) {
    LendyBasicTextField(
        modifier = modifier,
        input = input,
        hint = "메일 입력",
        imeAction = imeAction,
        textStyle = LendyFontStyle.medium15,
        keyboardType = KeyboardType.Email,
        onValueChange = onValueChange,
        trailingIcon = {
            InputTextButton(
                enabled = enabled,
                buttonText = "인증요청",
                onClick = onButtonClick
            )
        }
    )
}

@Composable
fun LendyCodeTextField(
    modifier: Modifier = Modifier,
    input: String,
    imeAction: ImeAction,
    timer: String?,
    onValueChange: (String) -> Unit
) {
    LendyBasicTextField(
        modifier = modifier,
        input = input,
        hint = "인증코드 입력",
        imeAction = imeAction,
        textStyle = LendyFontStyle.medium15,
        keyboardType = KeyboardType.Text,
        onValueChange = onValueChange,
        trailingIcon = {
            if (timer != null) {
                Text(
                    text = timer,
                    style = LendyFontStyle.medium13,
                    color = Main
                )
            }
        }
    )
}

@Composable
private fun InputLabel(
    label: String
) {
    Text(
        text = label,
        style = LendyFontStyle.semi14
    )
}

@Composable
private fun ErrorMessage(
    modifier: Modifier = Modifier,
    errorType: InputErrorType
) {
    Text(
        modifier = modifier
            .padding(
                start = 2.dp,
                end = 2.dp,
                top = 2.dp
            )
            .fillMaxWidth(),
        text = stringResource(inputErrorMessage(errorType)),
        style = LendyFontStyle.medium12,
        color = Red
    )
}

@Composable
fun LendyBasicInput(
    modifier: Modifier = Modifier,
    label: String,
    errorType: InputErrorType,
    textField: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .padding(
                start = 30.dp,
                end = 30.dp
            )
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        InputLabel(label = label)
        textField()
        ErrorMessage(errorType = errorType)
    }
}

@Composable
fun LendyInput(
    label: String,
    input: String,
    hint: String,
    imeAction: ImeAction,
    errorType: InputErrorType,
    onValueChange: (String) -> Unit
) {
    LendyBasicInput(
        label = label,
        errorType = errorType,
        textField = {
            LendyTextField(
                input = input,
                hint = hint,
                imeAction = imeAction,
                onValueChange = onValueChange
            )
        }
    )
}

@Composable
fun LendyPasswordInput(
    label: String,
    input: String,
    hint: String,
    imeAction: ImeAction,
    errorType: InputErrorType,
    onValueChange: (String) -> Unit
) {
    LendyBasicInput(
        label = label,
        errorType = errorType,
        textField = {
            LendyPasswordTextField(
                input = input,
                hint = hint,
                imeAction = imeAction,
                onValueChange = onValueChange
            )
        }
    )
}

@Composable
fun LendyNumberInput(
    label: String,
    input: String,
    imeAction: ImeAction,
    errorType: InputErrorType,
    onValueChange: (String) -> Unit
) {
    LendyBasicInput(
        label = label,
        errorType = errorType,
        textField = {
            LendyNumberTextField(
                input = input,
                imeAction = imeAction,
                onValueChange = onValueChange
            )
        }
    )
}

@Composable
fun LendyMailInput(
    input: String,
    imeAction: ImeAction,
    errorType: InputErrorType,
    buttonEnabled: Boolean,
    onButtonClick: () -> Unit,
    onValueChange: (String) -> Unit
) {
    LendyBasicInput(
        label = "이메일",
        errorType = errorType,
        textField = {
            LendyMailTextField(
                input = input,
                imeAction = imeAction,
                enabled = buttonEnabled,
                onButtonClick = onButtonClick,
                onValueChange = onValueChange
            )
        }
    )
}

@Composable
fun LendyCodeInput(
    input: String,
    imeAction: ImeAction,
    errorType: InputErrorType,
    timer: String?,
    onValueChange: (String) -> Unit
) {
    LendyBasicInput(
        label = "인증 코드",
        errorType = errorType,
        textField = {
            LendyCodeTextField(
                input = input,
                imeAction = imeAction,
                timer = timer,
                onValueChange = onValueChange
            )
        }
    )
}
