package com.hseun.lendy_v2.utils

import android.util.Log
import retrofit2.Response

suspend fun <T> apiCall(
    tag: String,
    call: suspend () -> Response<T>
): Result<T> {
    return try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Result.success(body)
            } else {
                Result.failure(Exception("응답 바디 없음"))
            }
        } else {
            Log.e(tag, response.code().toString())
            Result.failure(Exception(response.code().toString()))
        }
    } catch (e: Exception) {
        Log.e(tag, e.message.toString())
        Result.failure(e)
    }
}