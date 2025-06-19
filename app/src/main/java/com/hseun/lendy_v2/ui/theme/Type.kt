package com.hseun.lendy_v2.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hseun.lendy_v2.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val Pretendard = FontFamily(
    listOf(
        Font(
            resId = R.font.pretendard_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resId = R.font.pretendard_medium,
            weight = FontWeight.Medium
        ),
        Font(
            resId = R.font.pretendard_semibold,
            weight = FontWeight.SemiBold
        ),
        Font(
            resId = R.font.pretendard_bold,
            weight = FontWeight.Bold
        )
    )
)

object LendyFontStyle {
    private val defaultTextColor = Black

    private fun createTextStyle(weight: FontWeight, size: Int) = TextStyle(
        fontFamily = Pretendard,
        fontWeight = weight,
        fontSize = size.sp,
        color = defaultTextColor
    )

    val regular12 = createTextStyle(FontWeight.Normal, 12)

    val medium10 = createTextStyle(FontWeight.Medium, 10)
    val medium12 = createTextStyle(FontWeight.Medium, 12)
    val medium13 = createTextStyle(FontWeight.Medium, 13)
    val medium14 = createTextStyle(FontWeight.Medium, 14)
    val medium15 = createTextStyle(FontWeight.Medium, 15)
    val medium16 = createTextStyle(FontWeight.Medium, 16)
    val medium17 = createTextStyle(FontWeight.Medium, 17)
    val medium18 = createTextStyle(FontWeight.Medium, 18)

    val semi11 = createTextStyle(FontWeight.SemiBold, 11)
    val semi12 = createTextStyle(FontWeight.SemiBold, 12)
    val semi13 = createTextStyle(FontWeight.SemiBold, 13)
    val semi14 = createTextStyle(FontWeight.SemiBold, 14)
    val semi15 = createTextStyle(FontWeight.SemiBold, 15)
    val semi16 = createTextStyle(FontWeight.SemiBold, 16)
    val semi18 = createTextStyle(FontWeight.SemiBold, 18)
    val semi20 = createTextStyle(FontWeight.SemiBold, 20)
    val semi21 = createTextStyle(FontWeight.SemiBold, 21)
    val semi22 = createTextStyle(FontWeight.SemiBold, 22)
    val semi24 = createTextStyle(FontWeight.SemiBold, 24)

    val bold15 = createTextStyle(FontWeight.Bold, 15)
    val bold18 = createTextStyle(FontWeight.Bold, 18)
    val bold20 = createTextStyle(FontWeight.Bold, 20)
}