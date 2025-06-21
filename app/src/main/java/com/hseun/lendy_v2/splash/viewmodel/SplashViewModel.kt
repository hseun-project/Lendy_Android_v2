package com.hseun.lendy_v2.splash.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hseun.lendy_v2.splash.repository.SplashRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: SplashRepositoryImpl
) : ViewModel() {
    var isAutoLoginCheck by mutableStateOf(false)
        private set
    var isAutoLoginSuccess by mutableStateOf<Boolean?>(null)
        private set

    fun autoLogin() {
        viewModelScope.launch {
            isAutoLoginSuccess = try {
                repository.autoLogin()
            } catch (e: Exception) {
                false
            } finally {
                isAutoLoginCheck = true
            }
        }
    }
}