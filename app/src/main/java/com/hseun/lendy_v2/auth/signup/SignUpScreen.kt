package com.hseun.lendy_v2.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.hseun.lendy_v2.auth.signup.viewmodel.SignUpViewModel
import com.hseun.lendy_v2.ui.AuthButton
import com.hseun.lendy_v2.ui.AuthLogo
import com.hseun.lendy_v2.ui.LendyAlertDialog
import com.hseun.lendy_v2.ui.LendyCodeInput
import com.hseun.lendy_v2.ui.LendyMailInput
import com.hseun.lendy_v2.ui.LendyPasswordInput
import com.hseun.lendy_v2.ui.theme.White
import com.hseun.lendy_v2.ui.utils.addFocusCleaner
import com.hseun.lendy_v2.utils.InputErrorType
import com.hseun.lendy_v2.utils.formatTimer

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
    navToIdentification: () -> Unit,
    navToLogin: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    val email = viewModel.email
    val code = viewModel.code
    val password = viewModel.password
    val checkPassword = viewModel.checkPassword

    val emailErrorType = viewModel.emailErrorType
    val codeErrorType = viewModel.codeErrorType
    val pwErrorType = viewModel.pwErrorType
    val checkPwErrorType = viewModel.checkPwErrorType

    val timer = viewModel.timer

    val isLoading = viewModel.isLoading
    val isSendEmailLoading = viewModel.isSendEmailLoading
    val isSendEmailSuccess = viewModel.isSendEmailSuccess
    val isSignUpSuccess = viewModel.isSignUpSuccess

    val emailButtonEnabled by remember(isSendEmailLoading, emailErrorType) {
        derivedStateOf {
            !isSendEmailLoading &&
                    email.isNotEmpty() &&
                    emailErrorType == InputErrorType.NONE
        }
    }
    val buttonEnabled by remember(isLoading, emailErrorType, pwErrorType, checkPwErrorType) {
        derivedStateOf {
            !isLoading &&
                    email.isNotEmpty() &&
                    code.isNotEmpty() &&
                    password.isNotEmpty() &&
                    emailErrorType == InputErrorType.NONE &&
                    pwErrorType == InputErrorType.NONE &&
                    checkPwErrorType == InputErrorType.NONE &&
                    codeErrorType == InputErrorType.NONE
        }
    }

    if (isSignUpSuccess == true) {
        navToIdentification()
    } else if (isSignUpSuccess == false) {
        LendyAlertDialog(
            title = "회원가입에 실패하였습니다",
            onClick = {
                viewModel.changeIsSignUpSuccess(null)
            }
        )
    }

    if (isSendEmailSuccess == false) {
        LendyAlertDialog(
            title = "인증 코드 전송에 실패하였습니다",
            onClick = {
                viewModel.changeIsSendMailSuccess(null)
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
                errorType = emailErrorType,
                buttonEnabled = emailButtonEnabled,
                onButtonClick = {
                    viewModel.onSendCodeClick()
                },
                onValueChange = { input ->
                    viewModel.onMailChange(input)
                }
            )
            LendyCodeInput(
                input = code,
                imeAction = ImeAction.Next,
                errorType = codeErrorType,
                timer = formatTimer(timer),
                onValueChange = { input ->
                    viewModel.onCodeChange(input)
                }
            )
            LendyPasswordInput(
                label = stringResource(id = R.string.auth_pw),
                input = password,
                hint = stringResource(id = R.string.auth_pw), 
                imeAction = ImeAction.Next, 
                errorType = pwErrorType,
                onValueChange = { input -> 
                    viewModel.onPasswordChange(input)
                }
            )
            LendyPasswordInput(
                label = stringResource(id = R.string.auth_pw_check),
                input = checkPassword, 
                hint = stringResource(id = R.string.auth_pw_check), 
                imeAction = ImeAction.Done, 
                errorType = checkPwErrorType,
                onValueChange = { input ->
                    viewModel.onCheckPasswordChange(input)
                }
            )
        }
        AuthButton(
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(
                    start = 30.dp,
                    end = 30.dp
                ),
            enabled = buttonEnabled,
            buttonText = stringResource(id = R.string.signup),
            isNotText = stringResource(id = R.string.signup_is_member),
            moveToWhereText = stringResource(id = R.string.signup_go_login),
            onButtonClick = {
                viewModel.onSignUpClick()
            },
            onTextClick = navToLogin
        )
    }
}