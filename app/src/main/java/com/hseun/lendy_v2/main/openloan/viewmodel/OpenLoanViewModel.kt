package com.hseun.lendy_v2.main.openloan.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hseun.lendy_v2.main.openloan.repository.OpenLoanRepository
import com.hseun.lendy_v2.network.model.loan.RequestListItemData
import kotlinx.coroutines.launch
import javax.inject.Inject

class OpenLoanViewModel @Inject constructor(
    private val repository: OpenLoanRepository
) : ViewModel() {
    var requestList by mutableStateOf<List<RequestListItemData>>(listOf())

    var isLoading by mutableStateOf(false)

    fun getInfo() {
        viewModelScope.launch {
            isLoading = true
            val result = repository.getOpenLoanList()
            result.onSuccess {
                requestList = it
            }
            isLoading = false
        }
    }
}