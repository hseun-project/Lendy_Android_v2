package com.hseun.lendy_v2.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hseun.lendy_v2.R
import com.hseun.lendy_v2.auth.login.viewmodel.LoginViewModel
import com.hseun.lendy_v2.ui.AuthButton
import com.hseun.lendy_v2.ui.AuthLogo
import com.hseun.lendy_v2.ui.LendyAlertDialog
import com.hseun.lendy_v2.ui.LendyMailInput
import com.hseun.lendy_v2.ui.LendyPasswordInput
import com.hseun.lendy_v2.ui.theme.White
import com.hseun.lendy_v2.ui.utils.addFocusCleaner
import com.hseun.lendy_v2.utils.InputErrorType

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navToMain: () -> Unit,
    navToSignUp: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    val email = viewModel.email
    val password = viewModel.password

    val isLoading = viewModel.isLoading
    val isLoginSuccess = viewModel.isLoginSuccess

    val loginErrorType = if (isLoginSuccess == false) InputErrorType.WRONG_MAIL_OR_PW else InputErrorType.NONE
    val buttonEnabled by remember(isLoading, email, password) {
        derivedStateOf {
            !isLoading &&
                    email.isNotEmpty() &&
                    password.isNotEmpty()
        }
    }

    if (isLoginSuccess == true) {
        navToMain()
    } else if (isLoginSuccess == false) {
        LendyAlertDialog(
            title = "로그인에 실패하였습니다",
            onClick = {
                viewModel.changeIsLoginSuccess(null)
            }
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .addFocusCleaner(focusManager)
    ) {
        Column(
            modifier = modifier.align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthLogo()
            LendyMailInput(
                input = email,
                imeAction = ImeAction.Next,
                onValueChange = { input ->
                    viewModel.onEmailChange(input)
                }
            )
            LendyPasswordInput(
                label = stringResource(id = R.string.auth_pw),
                input = password,
                hint = stringResource(id = R.string.auth_pw),
                imeAction = ImeAction.Done,
                errorType = loginErrorType,
                onValueChange = { input ->
                    viewModel.onPasswordChange(input)
                }
            )
        }
        AuthButton(
            enabled = buttonEnabled,
            loading = isLoading,
            buttonText = stringResource(id = R.string.login),
            isNotText = stringResource(id = R.string.login_is_not_member),
            moveToWhereText = stringResource(id = R.string.login_go_signup),
            onButtonClick = {
                viewModel.onLoginClick()
            },
            onTextClick = navToSignUp
        )
    }
}