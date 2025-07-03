package com.hseun.lendy_v2.main.request.apply.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hseun.lendy_v2.main.request.apply.repository.ApplyRepository
import com.hseun.lendy_v2.network.model.loan.BondListItemData
import com.hseun.lendy_v2.utils.ApplyLoan
import com.hseun.lendy_v2.utils.DuringType
import com.hseun.lendy_v2.utils.InputErrorType
import com.hseun.lendy_v2.utils.formatMoney
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import kotlin.math.floor

class ApplyViewModel @Inject constructor(
    private val repository: ApplyRepository
) : ViewModel() {
    private var loanType by mutableStateOf(ApplyLoan.PRIVATE_LOAN)
    var keyword by mutableStateOf("")

    private var moneyNumber by mutableIntStateOf(0)
    var money by mutableStateOf("")

    private var interestNumber by mutableFloatStateOf(0f)
    var interest by mutableStateOf("")

    private var duringNumber by mutableIntStateOf(0)
    var during by mutableStateOf("")

    var duringType by mutableStateOf(DuringType.DAY)
    var bondList: List<BondListItemData> by mutableStateOf(listOf())

    var moneyErrorType by mutableStateOf(InputErrorType.NONE)
    var interestErrorType by mutableStateOf(InputErrorType.NONE)
    var duringErrorType by mutableStateOf(InputErrorType.NONE)

    var isLoading by mutableStateOf(false)
    private var selectedUser by mutableStateOf<BondListItemData?>(null)
    var getBondListSuccess by mutableStateOf<Boolean?>(null)
    var applySuccess by mutableStateOf<Boolean?>(null)

    fun onChangeDropdownOption(selected: String) {
        loanType = if (selected == "개인") ApplyLoan.PRIVATE_LOAN else ApplyLoan.PUBLIC_LOAN
    }
    fun onChangeKeyword(input: String) {
        keyword = input
        if (getBondListSuccess != null) {
            getBondListSuccess = null
        }
    }
    fun onChangeSelectedUser(user: BondListItemData) {
        selectedUser = user
        keyword = "${user.name} (${user.email})"
    }
    fun onChangeMoney(input: String) {
        val moneyStr = input.replace(",", "")
        val moneyInt = if (moneyStr.isDigitsOnly()) moneyStr.toInt() else 0
        moneyErrorType = if (moneyInt <= 0) {
            InputErrorType.IS_NOT_DIGIT
        } else if (moneyInt >= 1000000) {
            InputErrorType.OVER_MAX_PRICE
        } else {
            InputErrorType.NONE
        }
        moneyNumber = moneyInt
        money = formatMoney(moneyNumber)
    }
    fun onChangeInterest(input: String) {
        val interestFloat = input.toFloatOrNull()
        interestErrorType = if (interestFloat == null || interestFloat < 0) {
            InputErrorType.IS_NOT_DIGIT
        } else if (interestFloat >= 20) {
            InputErrorType.OVER_INTEREST
        } else {
            InputErrorType.NONE
        }
        interestNumber = interestFloat ?: 0f
        interest = String.format(Locale.getDefault(), "%.2f", interestNumber)
    }
    fun onChangeDuringType(value: DuringType) {
        duringType = value
    }
    fun onChangeDuring(input: String) {
        val duringInt = if (input.isDigitsOnly()) input.toInt() else 0
        duringErrorType = if (duringInt <= 0) InputErrorType.IS_NOT_DIGIT else InputErrorType.NONE
        during = input
        duringNumber = duringInt
    }
    fun onChangeGetBondSuccess(value: Boolean?) {
        getBondListSuccess = value
    }
    fun onChangeApplySuccess(value: Boolean?) {
        applySuccess = value
    }

    fun onSearchUser() {
        viewModelScope.launch {
            val result = repository.getBondList(keyword.trim())
            result.onSuccess {
                getBondListSuccess = true
                bondList = it
            }.onFailure {
                getBondListSuccess = false
            }
        }
    }
    fun applyLoan() {
        if (keyword == "${selectedUser?.name} (${selectedUser?.email})") {
            return
        }
        viewModelScope.launch {
            val result = repository.applyLoan(loanType, moneyNumber, interest, duringType, duringNumber, selectedUser?.id)
            result.onSuccess {
                applySuccess = true
            }.onFailure {
                applySuccess = false
            }
        }
    }
}