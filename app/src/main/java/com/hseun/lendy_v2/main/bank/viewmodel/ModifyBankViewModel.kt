package com.hseun.lendy_v2.main.bank.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hseun.lendy_v2.main.bank.repository.BankRepository
import com.hseun.lendy_v2.utils.InputErrorType
import kotlinx.coroutines.launch
import javax.inject.Inject

class ModifyBankViewModel @Inject constructor(
    private val repository: BankRepository
) : ViewModel() {
    var bankName by mutableStateOf("")
    var bankNumber by mutableStateOf("")

    var bankNumberErrorType by mutableStateOf(InputErrorType.NONE)

    var isLoading by mutableStateOf(false)
    var isModifySuccess by mutableStateOf<Boolean?>(null)

    fun onChangeBankName(value: String) {
        bankName = value
    }
    fun onChangeBankNumber(input: String) {
        bankNumber = input
        bankNumberErrorType = if (bankNumber.isDigitsOnly()) InputErrorType.NONE else InputErrorType.IS_NOT_DIGIT
    }
    fun onChangeModifySuccess(value: Boolean?) {
        isModifySuccess = value
    }

    fun onClickModifyBank() {
        viewModelScope.launch {
            isLoading = true
            val result = repository.modifyBank(bankName, bankNumber)
            result.onSuccess {
                isModifySuccess = true
            }.onFailure {
                isModifySuccess = false
            }
            isLoading = false
        }
    }
}