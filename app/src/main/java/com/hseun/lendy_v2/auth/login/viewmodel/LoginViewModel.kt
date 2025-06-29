package com.hseun.lendy_v2.auth.login.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hseun.lendy_v2.auth.login.repository.LoginRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var isLoginSuccess by mutableStateOf<Boolean?>(null)

    fun onEmailChange(input: String) {
        email = input
    }
    fun onPasswordChange(input: String) {
        password = input
    }
    fun changeIsLoginSuccess(value: Boolean?) {
        isLoginSuccess = value
    }

    fun onLoginClick() {
        viewModelScope.launch {
            isLoading = true
            val result = repository.login(email, password)
            isLoading = false
            result.onSuccess {
                isLoginSuccess = true
            }.onFailure {
                isLoginSuccess = false
            }
        }
    }
}