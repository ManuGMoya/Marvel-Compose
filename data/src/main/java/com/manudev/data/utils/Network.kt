package com.manudev.data.utils

import com.manudev.domain.APIResponseStatus
import retrofit2.Response

object Network {
    fun <T> Response<T>.safeCall(): APIResponseStatus<T> {
        return if (this.isSuccessful) {
            APIResponseStatus.Success(this.body()!!)
        } else {
            APIResponseStatus.Error("Error: ${this.code()}")
        }
    }
}
