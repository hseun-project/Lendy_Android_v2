package com.hseun.lendy_v2.auth.signup.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hseun.lendy_v2.auth.signup.repository.SignUpRepository
import com.hseun.lendy_v2.utils.InputErrorType
import com.hseun.lendy_v2.utils.InputRegexType
import com.hseun.lendy_v2.utils.checkInputRegex
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val repository: SignUpRepository
) : ViewModel() {
    var email by mutableStateOf("")
    private var sendEmail by mutableStateOf("")
    var code by mutableStateOf("")
    var password by mutableStateOf("")
    var checkPassword by mutableStateOf("")

    var timer by mutableIntStateOf(0)

    var isLoading by mutableStateOf(false)
    var isSendEmailLoading by mutableStateOf(false)
    var isSignUpSuccess by mutableStateOf<Boolean?>(null)
    var isSendEmailSuccess by mutableStateOf<Boolean?>(null)

    var emailErrorType by mutableStateOf(InputErrorType.NONE)
    var codeErrorType by mutableStateOf(InputErrorType.NONE)
    var pwErrorType by mutableStateOf(InputErrorType.NONE)
    var checkPwErrorType by mutableStateOf(InputErrorType.NONE)

    fun onEmailChange(input: String) {
        email = input
        emailErrorType = if (checkInputRegex(InputRegexType.MAIL, input)) InputErrorType.NONE else InputErrorType.MAIL_REGEX
    }
    fun onCodeChange(input: String) {
        code = input
        codeErrorType = InputErrorType.NONE
    }
    fun onPasswordChange(input: String) {
        password = input
        pwErrorType = if (checkInputRegex(InputRegexType.PASSWORD, input)) InputErrorType.NONE else InputErrorType.PW_REGEX
        checkPwErrorType = if (checkPassword.isEmpty() || password == checkPassword) InputErrorType.NONE else InputErrorType.NOT_SAME_PW
    }
    fun onCheckPasswordChange(input: String) {
        checkPassword = input
        checkPwErrorType = if (checkPassword.isEmpty() || password == checkPassword) InputErrorType.NONE else InputErrorType.NOT_SAME_PW
    }

    fun changeIsSendMailSuccess(value: Boolean?) {
        isSendEmailSuccess = value
    }
    fun changeIsSignUpSuccess(value: Boolean?) {
        isSignUpSuccess = value
    }

    fun onSendCodeClick() {
        sendEmail = email
        codeErrorType = InputErrorType.NONE
        viewModelScope.launch {
            isSendEmailLoading = true
            val result = repository.sendMail(email)
            isSendEmailLoading = false
            result.onSuccess {
                timer = 600
                isSendEmailSuccess = true
            }.onFailure {
                if (it.message == "409") {
                    emailErrorType = InputErrorType.OVERLAP_MAIL
                } else {
                    isSendEmailSuccess = false
                }
            }
        }
    }

    fun onSignUpClick() {
        if (email != sendEmail) {
            emailErrorType = InputErrorType.CHANGE_MAIL
            return
        }

        viewModelScope.launch {
            isLoading = true
            val result = repository.signUp(email, code, password)
            isLoading = false
            result.onSuccess {
                isSignUpSuccess = true
            }.onFailure {
                if (it.message == "408") {
                    codeErrorType = InputErrorType.CODE_TIMEOUT
                } else if (it.message == "409") {
                    codeErrorType = InputErrorType.WRONG_CODE
                } else {
                    isSignUpSuccess = false
                }
            }
        }
    }
}