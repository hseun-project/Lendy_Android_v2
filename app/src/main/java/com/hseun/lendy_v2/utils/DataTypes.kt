package com.hseun.lendy_v2.utils

import androidx.compose.ui.graphics.Color
import com.hseun.lendy_v2.ui.theme.Blue
import com.hseun.lendy_v2.ui.theme.Green
import com.hseun.lendy_v2.ui.theme.Main
import com.hseun.lendy_v2.ui.theme.Purple
import com.hseun.lendy_v2.ui.theme.Red
import com.hseun.lendy_v2.ui.theme.TagBlue
import com.hseun.lendy_v2.ui.theme.TagPurple

enum class DuringType {
    DAY,
    MONTH;

    val displayText: String
        get() = when(this) {
            DAY -> "일"
            MONTH -> "개월"
        }
}

enum class ApplyLoan {
    PRIVATE_LOAN,
    PUBLIC_LOAN;

    val displayText: String
        get() = when(this) {
            PRIVATE_LOAN -> "개인"
            PUBLIC_LOAN -> "공개"
        }
    val displayTextColor: Color
        get() = when(this) {
            PRIVATE_LOAN -> Purple
            PUBLIC_LOAN -> Main
        }
    val displayBackgroundColor: Color
        get() = when(this) {
            PRIVATE_LOAN -> TagPurple
            PUBLIC_LOAN -> TagBlue
        }
}

enum class ApplyState {
    APPROVAL,
    PENDING,
    REJECTED;

    val displayText: String
        get() = when(this) {
            APPROVAL -> "승인"
            PENDING -> "대기"
            REJECTED -> "거절"
        }
    val displayTextColor: Color
        get() = when(this) {
            APPROVAL -> Green
            PENDING -> Blue
            REJECTED -> Red
        }
}

val bankList = listOf(
    "NH 농협",
    "카카오뱅크",
    "KB 국민",
    "토스뱅크",
    "신한",
    "우리",
    "IBK 기업",
    "하나",
    "새마을",
    "케이뱅크",
    "SC 제일",
    "신협",
    "수협",
    "우체국",
    "경남",
    "광주",
    "부산",
    "IM 뱅크 (대구)",
    "전북",
    "제주"
)