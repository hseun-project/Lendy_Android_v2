package com.hseun.lendy_v2.auth.identification.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hseun.lendy_v2.auth.identification.repository.IdentificationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class IdentificationViewModel @Inject constructor(
    private val repository: IdentificationRepository
) : ViewModel() {
    var url by mutableStateOf("")

    var isNavigateToLogin by mutableStateOf(false)
    var isGetUrl by mutableStateOf<Boolean?>(null)
    var isLoading by mutableStateOf(false)

    fun onUrlLoaded(url: String) {
        if (url.startsWith("http://localhost:8080/open")) {
            isNavigateToLogin = true
        }
    }

    fun getStartUrl() {
        viewModelScope.launch {
            isLoading = true
            val result = repository.getUrl()
            isLoading = false
            result.onSuccess {
                url = it.url
                isGetUrl = true
            }.onFailure {
                isGetUrl = false
            }
        }
    }
}