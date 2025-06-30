package com.hseun.lendy_v2.main.bank

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hseun.lendy_v2.main.bank.viewmodel.ModifyBankViewModel
import com.hseun.lendy_v2.ui.LargeDropdown
import com.hseun.lendy_v2.ui.LendyAlertDialog
import com.hseun.lendy_v2.ui.LendyButton
import com.hseun.lendy_v2.ui.LendyInput
import com.hseun.lendy_v2.ui.LendyTopBar
import com.hseun.lendy_v2.ui.theme.White
import com.hseun.lendy_v2.ui.utils.addFocusCleaner
import com.hseun.lendy_v2.utils.InputErrorType
import com.hseun.lendy_v2.utils.bankList

@Composable
fun ModifyBankScreen(
    modifier: Modifier = Modifier,
    viewModel: ModifyBankViewModel = hiltViewModel(),
    bankName: String,
    bankNumber: String,
    navToGoBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    val bank = viewModel.bankName
    val number = viewModel.bankNumber

    val numberErrorType = viewModel.bankNumberErrorType
    val isLoading = viewModel.isLoading
    val isModifySuccess = viewModel.isModifySuccess

    val buttonEnabled = remember(bank, number) {
        !isLoading &&
                bank.isNotEmpty() &&
                number.isNotEmpty() &&
                bank != bankName &&
                number != bankNumber
    }

    LaunchedEffect(Unit) {
        viewModel.onChangeBankName(bankName)
        viewModel.onChangeBankNumber(bankNumber)
    }

    if (isModifySuccess == true) {
        navToGoBack()
    } else if (isModifySuccess == false) {
        LendyAlertDialog(
            title = "계좌 수정에 실패하였습니다",
            onClick = {
                viewModel.onChangeModifySuccess(null)
            }
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .addFocusCleaner(focusManager)
    ) {
        Column(
            modifier = modifier.align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.height(80.dp))
            LendyInput(
                label = "계좌번호",
                input = number,
                hint = "계좌번호",
                imeAction = ImeAction.Done,
                errorType = numberErrorType,
                keyboardType = KeyboardType.Number,
                onValueChange = { input ->
                    viewModel.onChangeBankNumber(input)
                }
            )
            LargeDropdown(
                options = bankList,
                selectedOption = bank,
                label = "은행 선택",
                onOptionSelected = { selected ->
                    viewModel.onChangeBankName(selected)
                }
            )
        }
        LendyButton(
            modifier = modifier
                .padding(
                    bottom = 40.dp
                )
                .align(Alignment.BottomCenter),
            enabled = buttonEnabled,
            text = "계좌 수정",
            onClick = {
                viewModel.onClickModifyBank()
            }
        )
    }
}