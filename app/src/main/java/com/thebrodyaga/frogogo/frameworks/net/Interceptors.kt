package com.thebrodyaga.frogogo.frameworks.net

import com.thebrodyaga.frogogo.R
import com.thebrodyaga.frogogo.domain.ErrorHandler
import com.thebrodyaga.frogogo.screen.common.ResourceManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Для запросов к нашему апи
 */
class NetErrorInterceptor(
    private val errorHandler: ErrorHandler,
    private val resourceManager: ResourceManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = try {
            chain.proceed(chain.request())

        } catch (e: IOException) {
            throw IOException(resourceManager.getString(R.string.network_error))
        }
        if (response.code() in 400..500) throw errorHandler.proceedHttp(response)
        return response
    }
}