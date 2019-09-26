package com.thebrodyaga.frogogo.domain

import com.thebrodyaga.frogogo.screen.common.ResourceManager
import com.thebrodyaga.frogogo.screen.common.httpCodeMessage
import com.thebrodyaga.frogogo.screen.common.userMessage
import okhttp3.Response
import javax.inject.Inject


class ErrorHandler(private val resourceManager: ResourceManager) {

    @JvmOverloads
    fun proceed(error: Throwable, messageListener: (String) -> Unit = {}) {
        error.printStackTrace()
        when (error) {
            is ApiError -> messageListener(error.userMessage(resourceManager))
            else -> messageListener(error.userMessage(resourceManager))
        }
    }

    fun proceedHttp(response: Response): ApiError =
        when (response.code) {
            422 -> ApiError(response.code, response.message)
            else -> ApiError(response.code, response.code.httpCodeMessage(resourceManager))
        }
}
