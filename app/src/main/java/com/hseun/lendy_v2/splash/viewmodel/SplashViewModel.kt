package com.hseun.lendy_v2.splash.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hseun.lendy_v2.splash.repository.SplashRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val repository: SplashRepository
) : ViewModel() {
    var isAutoLoginCheck by mutableStateOf(false)
    var isAutoLoginSuccess by mutableStateOf(false)

    fun autoLogin() {
        viewModelScope.launch {
            isAutoLoginSuccess = repository.autoLogin()
            isAutoLoginCheck = true
        }
    }
}