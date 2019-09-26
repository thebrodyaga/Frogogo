package com.thebrodyaga.frogogo.screen.common

import com.thebrodyaga.frogogo.R
import com.thebrodyaga.frogogo.domain.ApiError
import retrofit2.HttpException
import java.io.IOException


fun Throwable.userMessage(resourceManager: ResourceManager): String = when (this) {
    is HttpException -> this.code().httpCodeMessage(resourceManager)
    is ApiError ->
        this.errorMessage
            ?: this.errorMessageRes?.run { resourceManager.getString(this) }
            ?: this.errorCode.httpCodeMessage(resourceManager)
    is IOException -> resourceManager.getString(R.string.network_error)
    else -> resourceManager.getString(R.string.unknown_error)
}

fun Int.httpCodeMessage(resourceManager: ResourceManager): String =
    when (this) {
        400 -> resourceManager.getString(R.string.bad_request_error)
        else -> resourceManager.getString(R.string.network_error)
    }