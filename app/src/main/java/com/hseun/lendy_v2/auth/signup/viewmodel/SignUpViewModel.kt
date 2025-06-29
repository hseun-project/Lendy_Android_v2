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
    var mail by mutableStateOf("")
    var sendMail by mutableStateOf("")
    var code by mutableStateOf("")
    var password by mutableStateOf("")
    var checkPassword by mutableStateOf("")

    var timer by mutableIntStateOf(0)

    var isLoading by mutableStateOf(false)
    var isSendMailLoading by mutableStateOf(false)
    var isSignUpSuccess by mutableStateOf<Boolean?>(null)
    var isSendMailSuccess by mutableStateOf<Boolean?>(null)

    var mailErrorType by mutableStateOf(InputErrorType.NONE)
    var codeErrorType by mutableStateOf(InputErrorType.NONE)
    var pwErrorType by mutableStateOf(InputErrorType.NONE)
    var checkPwErrorType by mutableStateOf(InputErrorType.NONE)

    fun onMailChange(input: String) {
        mail = input
        mailErrorType = if (checkInputRegex(InputRegexType.MAIL, input)) InputErrorType.NONE else InputErrorType.MAIL_REGEX
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
        isSendMailSuccess = value
    }
    fun changeIsSignUpSuccess(value: Boolean?) {
        isSignUpSuccess = value
    }

    fun onSendCodeClick() {
        sendMail = mail
        viewModelScope.launch {
            isSendMailLoading = true
            val result = repository.sendMail(mail)
            isSendMailLoading = false
            result.onSuccess {
                timer = 600
                isSendMailSuccess = true
            }.onFailure {
                if (it.message == "409") {
                    mailErrorType = InputErrorType.OVERLAP_MAIL
                } else {
                    isSendMailSuccess = false
                }
            }
        }
    }

    fun onSignUpClick() {
        if (mail != sendMail) {
            mailErrorType = InputErrorType.CHANGE_MAIL
            return
        }

        viewModelScope.launch {
            isLoading = true
            val result = repository.signUp(mail, code, password)
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