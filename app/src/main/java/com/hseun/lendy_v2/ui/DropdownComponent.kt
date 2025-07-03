package com.hseun.lendy_v2.ui

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.hseun.lendy_v2.R
import com.hseun.lendy_v2.ui.theme.Gray400
import com.hseun.lendy_v2.ui.theme.LendyFontStyle
import com.hseun.lendy_v2.ui.theme.Main
import com.hseun.lendy_v2.ui.theme.White

private val dropdownWidth = 120.dp
private val dropdownAnimationSpec = tween<IntSize>(
    durationMillis = 300,
    easing = FastOutSlowInEasing
)

@Composable
private fun DropdownHeader(
    modifier: Modifier = Modifier,
    selectedOption: String,
    isExpanded: Boolean,
    onGloballyPositioned: (LayoutCoordinates) -> Unit,
    onHeaderClick: () -> Unit
) {
    Box(
        modifier = modifier
            .width(dropdownWidth)
            .wrapContentHeight()
            .background(
                color = White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = Main,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onHeaderClick()
            }
            .padding(
                top = 8.dp,
                bottom = 8.dp,
                end = 8.dp
            )
            .onGloballyPositioned { coordinates ->
                onGloballyPositioned(coordinates)
            }
    ) {
        Text(
            modifier = modifier
                .align(Alignment.Center)
                .padding(
                    end = 12.dp
                ),
            text = selectedOption,
            style = LendyFontStyle.semi18,
            color = Main
        )
        Icon(
            modifier = modifier
                .width(26.dp)
                .align(Alignment.CenterEnd)
                .rotate(if (isExpanded) 180f else 0f),
            painter = painterResource(id = R.drawable.icon_dropdown),
            tint = Main,
            contentDescription = "Dropdown Icon"
        )
    }
}

@Composable
private fun DropdownMenuItem(
    modifier: Modifier = Modifier,
    option: String,
    onOptionClick: () -> Unit
) {
    Text(
        modifier = modifier
            .width(dropdownWidth)
            .wrapContentHeight()
            .clickable {
                onOptionClick()
            }
            .padding(
                top = 10.dp,
                bottom = 10.dp
            ),
        text = option,
        style = LendyFontStyle.medium16,
        color = Main,
        textAlign = TextAlign.Center
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun DropdownMenuContent(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    options: List<String>,
    onOptionClick: (String) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
            .width(dropdownWidth)
            .wrapContentHeight()
            .background(
                color = White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = Main,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .animateContentSize(
                animationSpec = dropdownAnimationSpec
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isExpanded) {
                options.forEachIndexed { index, option ->
                    if (index > 0) {
                        HorizontalDivider(
                            thickness = 0.4.dp,
                            color = Main
                        )
                    }
                    DropdownMenuItem(
                        option = option,
                        onOptionClick = {
                            onOptionClick(option)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DropdownMenu(
    isExpanded: Boolean,
    offset: IntOffset,
    options: List<String>,
    onDismissRequest: () -> Unit,
    onOptionClick: (String) -> Unit
) {
    Popup(
        alignment = Alignment.TopCenter,
        offset = offset,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuContent(
            isExpanded = isExpanded,
            options = options,
            onOptionClick = onOptionClick
        )
    }
}

@Composable
fun Dropdown(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isHeaderClick by remember { mutableStateOf(false) }

    var headerHeight by remember { mutableIntStateOf(0) }
    val addOffset = with(LocalDensity.current) { 20.dp.toPx() }.toInt()

    Column(
        modifier = modifier
    ) {
        DropdownHeader(
            selectedOption = selectedOption,
            isExpanded = isExpanded,
            onGloballyPositioned = { coordinates ->
                if (headerHeight == 0) {
                    headerHeight = coordinates.size.height
                }
            },
            onHeaderClick = {
                isExpanded = !isExpanded
                isHeaderClick = true
            }
        )
        DropdownMenu(
            isExpanded = isExpanded,
            offset = IntOffset(0, headerHeight + addOffset),
            options = options,
            onDismissRequest = {
                if (!isHeaderClick) {
                    isExpanded = false
                }
                isHeaderClick = false
            },
            onOptionClick = { option ->
                onOptionSelected(option)
                isExpanded = false
            }
        )
    }
}

@Composable
private fun LargeDropdownHeader(
    modifier: Modifier = Modifier,
    selectedOption: String,
    isExpanded: Boolean,
    onGloballyPositioned: (LayoutCoordinates) -> Unit,
    onHeaderClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(
                start = 30.dp,
                end = 30.dp
            )
            .fillMaxWidth()
            .background(
                color = White,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = Main,
                shape = RoundedCornerShape(4.dp)
            )
            .clip(
                shape = RoundedCornerShape(4.dp)
            )
            .clickable {
                onHeaderClick()
            }
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 14.dp,
                bottom = 14.dp
            )
            .onGloballyPositioned { coordinates ->
                onGloballyPositioned(coordinates)
            }
    ) {
        Text(
            modifier = modifier.align(Alignment.CenterStart),
            text = selectedOption,
            style = LendyFontStyle.semi16,
            color = Main,
        )
        Icon(
            painter = painterResource(id = R.drawable.icon_dropdown),
            contentDescription = "Dropdown Icon",
            tint = Main,
            modifier = modifier
                .align(Alignment.CenterEnd)
                .rotate(if (isExpanded) 180f else 0f)
        )
    }
}

@Composable
private fun LargeDropdownMenuItem(
    modifier: Modifier = Modifier,
    option: String,
    onOptionClick: () -> Unit
) {
    Text(
        text = option,
        style = LendyFontStyle.medium14,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onOptionClick()
            }
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 12.dp
            )
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun LargeDropdownMenuContent(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    options: List<String>,
    onOptionClick: (String) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
            .padding(
                start = 30.dp,
                end = 30.dp
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = White,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 0.6.dp,
                color = Gray400,
                shape = RoundedCornerShape(4.dp)
            )
            .clip(
                shape = RoundedCornerShape(4.dp)
            )
            .animateContentSize(
                animationSpec = dropdownAnimationSpec
            )
    ) {
        if (isExpanded) {
            LazyColumn(
                modifier = modifier.height(240.dp)
            ) {
                itemsIndexed(options) { index, option ->
                    if (index > 0) {
                        HorizontalDivider(
                            thickness = 0.6.dp,
                            color = Gray400
                        )
                    }
                    LargeDropdownMenuItem(
                        option = option,
                        onOptionClick = {
                            onOptionClick(option)
                        }
                    )
                }
            }
        }
    }
}


@Composable
private fun LargeDropdownMenu(
    isExpanded: Boolean,
    offset: IntOffset,
    options: List<String>,
    onDismissRequest: () -> Unit,
    onOptionClick: (String) -> Unit
) {
    Popup(
        alignment = Alignment.TopCenter,
        offset = offset,
        onDismissRequest = onDismissRequest
    ) {
        LargeDropdownMenuContent(
            isExpanded = isExpanded,
            options = options,
            onOptionClick = onOptionClick
        )
    }
}

@Composable
fun LargeDropdown(
    options: List<String>,
    selectedOption: String,
    label: String,
    onOptionSelected: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isHeaderClicked by remember { mutableStateOf(false) }

    var headerHeight by remember { mutableIntStateOf(0) }
    val addYOffset = with(LocalDensity.current) { 32.dp.toPx() }.toInt()

    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        InputLabel(label = label)
        LargeDropdownHeader(
            selectedOption = selectedOption,
            isExpanded = isExpanded,
            onGloballyPositioned = { coordinates ->
                if (headerHeight == 0) {
                    headerHeight = coordinates.size.height
                }
            },
            onHeaderClick = {
                isExpanded = !isExpanded
                isHeaderClicked = true
            }
        )

        LargeDropdownMenu(
            isExpanded = isExpanded,
            offset = IntOffset(
                x = 0,
                y = headerHeight + addYOffset
            ),
            options = options,
            onDismissRequest = {
                if (!isHeaderClicked) {
                    isExpanded = false
                }
                isHeaderClicked = false
            },
            onOptionClick = { option ->
                onOptionSelected(option)
                isExpanded = false
            }
        )
    }
}