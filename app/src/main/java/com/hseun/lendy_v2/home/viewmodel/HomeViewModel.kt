package com.hseun.lendy_v2.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hseun.lendy_v2.home.repository.HomeRepository
import com.hseun.lendy_v2.network.model.loan.LentListItemData
import com.hseun.lendy_v2.network.model.loan.RequestListItemData
import com.hseun.lendy_v2.network.model.repay.RepayListItemData
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {
    var name by mutableStateOf("")
    var creditScore by mutableIntStateOf(0)
    var requestList: List<RequestListItemData> by mutableStateOf(listOf())
    var repayList: List<RepayListItemData> by mutableStateOf(listOf())
    var lentList: List<LentListItemData> by mutableStateOf(listOf())

    var isLoading by mutableStateOf(false)

    fun getInfo() {
        viewModelScope.launch {
            isLoading = true
            val userResult = async { repository.getUserInfo() }
            val requestResult = async { repository.getRequestList() }
            val repayResult = async { repository.getRepayList() }
            val lentResult = async { repository.getLentList() }

            userResult.await().onSuccess {
                creditScore = it.creditScore
                name = it.name
            }
            requestResult.await().onSuccess {
                requestList = it
            }
            repayResult.await().onSuccess {
                repayList = it
            }
            lentResult.await().onSuccess {
                lentList = it
            }
            isLoading = false
        }
    }
}