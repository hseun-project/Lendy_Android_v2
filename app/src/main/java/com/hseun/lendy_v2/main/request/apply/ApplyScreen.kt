package com.hseun.lendy_v2.main.request.apply

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hseun.lendy_v2.main.request.apply.viewmodel.ApplyViewModel
import com.hseun.lendy_v2.network.model.loan.BondListItemData
import com.hseun.lendy_v2.ui.Dropdown
import com.hseun.lendy_v2.ui.LendyAlertDialog
import com.hseun.lendy_v2.ui.LendyButton
import com.hseun.lendy_v2.ui.LendyInput
import com.hseun.lendy_v2.ui.LendyNumberInput
import com.hseun.lendy_v2.ui.theme.Gray400
import com.hseun.lendy_v2.ui.theme.Gray600
import com.hseun.lendy_v2.ui.theme.LendyFontStyle
import com.hseun.lendy_v2.ui.theme.Main
import com.hseun.lendy_v2.ui.theme.White
import com.hseun.lendy_v2.ui.utils.addFocusCleaner
import com.hseun.lendy_v2.ui.utils.dropShadow
import com.hseun.lendy_v2.utils.DuringType
import com.hseun.lendy_v2.utils.InputErrorType
import com.hseun.lendy_v2.utils.calculateInterest

@Composable
fun ApplyScreen(
    modifier: Modifier = Modifier,
    viewModel: ApplyViewModel = hiltViewModel(),
    navToBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    val applyLoanOption = listOf("개인", "공개")
    var selectedOption by remember { mutableStateOf(applyLoanOption[0]) }

    val keyword = viewModel.keyword
    val money = viewModel.money
    val interest = viewModel.interest
    val during = viewModel.during
    val duringType = viewModel.duringType
    val bondList = viewModel.bondList

    val moneyErrorType = viewModel.moneyErrorType
    val interestErrorType = viewModel.interestErrorType
    val duringErrorType = viewModel.duringErrorType

    val isLoading = viewModel.isLoading
    val getBondListSuccess = viewModel.getBondListSuccess
    val applySuccess = viewModel.applySuccess

    val buttonEnabled by remember(money, interest, during) {
        derivedStateOf {
            interest.isNotEmpty() &&
                    money.isNotEmpty() &&
                    during.isNotEmpty() &&
                    moneyErrorType == InputErrorType.NONE &&
                    interestErrorType == InputErrorType.NONE
        }
    }

    if (applySuccess == true) {
        navToBack()
    } else if (applySuccess == false) {
        LendyAlertDialog(
            title = "대출 신청에 실패하였습니다",
            onClick = {
                viewModel.onChangeApplySuccess(null)
            }
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .padding(
                start = 30.dp,
                end = 30.dp,
                top = 40.dp,
                bottom = 40.dp
            )
            .addFocusCleaner(focusManager)
    ) {
        Column(
            modifier = modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Dropdown(
                options = applyLoanOption,
                selectedOption = selectedOption,
                onOptionSelected = { selected ->
                    viewModel.onChangeDropdownOption(selected)
                    selectedOption = selected
                }
            )
            Spacer(modifier = modifier.height(14.dp))
            if (selectedOption == "개인") {
                SearchUser(
                    keyword = keyword,
                    users = bondList,
                    isExpanded = getBondListSuccess,
                    onExpandedChange = { value ->
                        viewModel.onChangeGetBondSuccess(value)
                    },
                    onKeywordChange = { input ->
                        viewModel.onChangeKeyword(input)
                    },
                    onSearch = {
                        viewModel.onSearchUser()
                    },
                    onUserChange = { user ->
                        viewModel.onChangeSelectedUser(user)
                    }
                )
            }
            LendyNumberInput(
                modifier = modifier.width(160.dp),
                label = "대출 금액",
                input = money,
                unitText = "원",
                imeAction = ImeAction.Next,
                errorType = moneyErrorType,
                onValueChange = { input ->
                    viewModel.onChangeMoney(input)
                }
            )
            LendyNumberInput(
                modifier = modifier.width(100.dp),
                label = "연 이자율",
                input = interest,
                unitText = "%",
                imeAction = ImeAction.Next,
                errorType = interestErrorType,
                onValueChange = { input ->
                    viewModel.onChangeInterest(input)
                }
            )
            DuringInput(
                selectedDuringType = duringType,
                during = during,
                duringErrorType = duringErrorType,
                onDuringTypeChange = { value ->
                    viewModel.onChangeDuringType(value)
                },
                onDuringChange = { input ->
                    viewModel.onChangeDuring(input)
                }
            )
        }
        Column(
            modifier = modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TotalBox(
                modifier = modifier,
                money = money,
                duringType = duringType,
                during = during,
                interest = interest
            )
            Spacer(modifier = modifier.height(26.dp))
            LendyButton(
                modifier = modifier,
                loading = isLoading,
                enabled = buttonEnabled,
                text = "대출 신청",
                onClick = {
                    viewModel.applyLoan()
                }
            )
        }
    }
}

@Composable
fun SearchUserItem(
    modifier: Modifier = Modifier,
    name: String,
    email: String,
    onOptionClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onOptionClick()
            }
            .padding(
                top = 12.dp,
                bottom = 12.dp,
                start = 16.dp,
                end = 16.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = name,
            style = LendyFontStyle.medium17
        )
        Text(
            text = email,
            style = LendyFontStyle.medium13,
            color = Gray600
        )
    }
}

@Composable
fun SearchUserMenu(
    modifier: Modifier = Modifier,
    isExpanded: Boolean?,
    options: List<BondListItemData>,
    onOptionClick: (BondListItemData) -> Unit
) {
    if (isExpanded != true || options.isEmpty()) return
    LazyColumn(
        modifier = modifier
            .padding(
                start = 30.dp,
                end = 30.dp
            )
            .heightIn(max = 220.dp)
            .fillMaxWidth()
            .background(
                color = White,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 0.6.dp,
                color = Gray400,
                shape = RoundedCornerShape(4.dp)
            )
            .clip(RoundedCornerShape(4.dp))
            .animateContentSize(
                animationSpec = tween<IntSize>(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
    ) {
        itemsIndexed(options) { index, item ->
            if (index > 0) {
                HorizontalDivider(
                    thickness = 0.4.dp,
                    color = Gray400
                )
            }
            SearchUserItem(
                name = item.name,
                email = item.email,
                onOptionClick = {
                    onOptionClick(item)
                }
            )
        }
    }
}

@Composable
fun SearchUser(
    modifier: Modifier = Modifier,
    keyword: String,
    users: List<BondListItemData>,
    isExpanded: Boolean?,
    onExpandedChange: (Boolean?) -> Unit,
    onSearch: () -> Unit,
    onKeywordChange: (String) -> Unit,
    onUserChange: (BondListItemData) -> Unit
) {
    Box {
        LendyInput(
            label = "신청 대상",
            input = keyword,
            hint = "이름 또는 이메일을 입력해주세요",
            imeAction = ImeAction.Search,
            errorType = InputErrorType.NONE,
            onValueChange = { input ->
                onKeywordChange(input)
            },
            onSearch = {
                onSearch()
            }
        )
        SearchUserMenu(
            modifier = modifier
                .padding(top = 64.dp)
                .align(Alignment.TopCenter),
            isExpanded = isExpanded,
            options = users,
            onOptionClick = { user ->
                onUserChange(user)
                onExpandedChange(null)
            }
        )
    }
}

@Composable
private fun TotalBoxTitle(
    modifier: Modifier = Modifier,
    money: String
) {
    Row(
        modifier = modifier
            .padding(
                start = 4.dp,
                end = 4.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "상환금",
            style = LendyFontStyle.semi16
        )
        Text(
            text = "${money}원",
            style = LendyFontStyle.bold18
        )
    }
}

@Composable
private fun TotalBoxContent(
    modifier: Modifier = Modifier,
    title: String,
    content: String
) {
    Row(
        modifier = modifier
            .padding(
                start = 8.dp,
                end = 8.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = LendyFontStyle.medium14,
            color = Gray600
        )
        Text(
            text = content,
            style = LendyFontStyle.medium14,
            color = Gray600
        )
    }
}

@Composable
private fun TotalBox(
    modifier: Modifier = Modifier,
    money: String,
    duringType: DuringType,
    during: String,
    interest: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .dropShadow()
            .background(
                color = White,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TotalBoxTitle(
            modifier = modifier.fillMaxWidth(),
            money = money
        )
        HorizontalDivider(
            modifier = modifier
                .padding(
                    top = 4.dp,
                    bottom = 4.dp
                ),
            thickness = 0.6.dp,
            color = Gray400
        )
        TotalBoxContent(
            modifier = modifier,
            title = "대출금",
            content = "${money}원"
        )
        TotalBoxContent(
            modifier = modifier,
            title = "예상 이자",
            content = "${calculateInterest(duringType, during.toInt(), money.toInt(), interest)}원"
        )
    }
}

@Composable
private fun DuringTypeItem(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier
            .padding(
                bottom = 20.dp
            )
            .width(74.dp)
            .wrapContentHeight()
            .background(
                color = if (isSelected) Main else White,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = Main,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(
                top = 6.dp,
                bottom = 6.dp
            )
            .clickable {
                onClick()
            },
        text = text,
        style = LendyFontStyle.medium16,
        color = if (isSelected) White else Main,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun DuringInput(
    modifier: Modifier = Modifier,
    selectedDuringType: DuringType,
    during: String,
    duringErrorType: InputErrorType,
    onDuringTypeChange: (DuringType) -> Unit,
    onDuringChange: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LendyNumberInput(
            modifier = modifier.width(100.dp),
            label = "상환 기한",
            input = during,
            unitText = "",
            imeAction = ImeAction.Done,
            errorType = duringErrorType,
            onValueChange = onDuringChange
        )
        DuringTypeItem(
            text = "일",
            isSelected = selectedDuringType == DuringType.DAY,
            onClick = {
                if (selectedDuringType != DuringType.DAY) {
                    onDuringTypeChange(DuringType.DAY)
                }
            }
        )
        DuringTypeItem(
            text = "개월",
            isSelected = selectedDuringType == DuringType.MONTH,
            onClick = {
                if (selectedDuringType != DuringType.MONTH) {
                    onDuringTypeChange(DuringType.MONTH)
                }
            }
        )
    }
}
