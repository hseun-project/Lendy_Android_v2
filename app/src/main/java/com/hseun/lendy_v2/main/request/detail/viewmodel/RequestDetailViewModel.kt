package com.hseun.lendy_v2.main.request.detail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hseun.lendy_v2.main.request.detail.repository.RequestDetailRepository
import com.hseun.lendy_v2.utils.ApplyState
import com.hseun.lendy_v2.utils.DuringType
import kotlinx.coroutines.launch
import javax.inject.Inject

class RequestDetailViewModel @Inject constructor(
    private val repository: RequestDetailRepository
) : ViewModel() {
    var debtName by mutableStateOf("")
    var money by mutableIntStateOf(0)
    var duringType by mutableStateOf(DuringType.DAY)
    var during by mutableIntStateOf(0)
    var interest by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var isConfirmSuccess by mutableStateOf<Boolean?>(null)
    var isCancelSuccess by mutableStateOf<Boolean?>(null)

    fun getInfo(id: Long) {
        viewModelScope.launch {
            isLoading = true
            val result = repository.getDetailInfo(id)
            result.onSuccess {
                debtName = it.debtName
                money = it.money
                duringType = it.duringType
                during = it.during
                interest = it.interest
            }
        }
    }

    fun onConfirmClick(id: Long) {
        viewModelScope.launch {
            val result = repository.changeRequestState(id, ApplyState.APPROVAL)
            result.onSuccess {
                isConfirmSuccess = true
            }.onFailure {
                isConfirmSuccess = false
            }
        }
    }
    fun onCancelClick(id: Long) {
        viewModelScope.launch {
            val result = repository.changeRequestState(id, ApplyState.REJECTED)
            result.onSuccess {
                isCancelSuccess = true
            }.onFailure {
                isCancelSuccess = false
            }
        }
    }

    fun changeConfirmSuccess(value: Boolean?) {
        isConfirmSuccess = value
    }
    fun changeCancelSuccess(value: Boolean?) {
        isCancelSuccess = value
    }
}