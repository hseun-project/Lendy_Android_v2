package com.hseun.lendy_v2.main.mypage.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hseun.lendy_v2.main.mypage.repository.MyPageRepository
import com.hseun.lendy_v2.network.model.user.ApplyListItemData
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyPageViewModel @Inject constructor(
    private val repository: MyPageRepository
) : ViewModel() {
    var email by mutableStateOf("")
    var name by mutableStateOf("")
    var creditScore by mutableIntStateOf(0)
    var bankName by mutableStateOf("")
    var bankNumber by mutableStateOf("")
    var money by mutableIntStateOf(0)
    var applyList by mutableStateOf<List<ApplyListItemData>>(listOf())

    var isLoading by mutableStateOf(false)
    var isLogoutSuccess by mutableStateOf<Boolean?>(null)

    fun getInfo() {
        viewModelScope.launch {
            isLoading = true
            val myInfoResult = async { repository.getUserInfo() }
            val applyLoanListResult = async { repository.getApplyLoanList() }

            myInfoResult.await().onSuccess {
                email = it.email
                name = it.name
                creditScore = it.creditScore
                bankName = it.bank.bankName
                bankNumber = it.bank.bankNumber
                money = it.bank.money
            }
            applyLoanListResult.await().onSuccess {
                applyList = it
            }
            isLoading = false
        }
    }

    fun logout() {
        viewModelScope.launch {
            val result = repository.logout()
            result.onSuccess {
                isLogoutSuccess = true
            }.onFailure {
                isLogoutSuccess = false
            }
        }
    }

    fun onChangeLogoutSuccess(value: Boolean?) {
        isLogoutSuccess = value
    }
}