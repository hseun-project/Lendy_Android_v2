package com.hseun.lendy_v2.network.model.loan

import com.hseun.lendy_v2.utils.ApplyState

data class ChangeStateRequest(
    val state: ApplyState
)
