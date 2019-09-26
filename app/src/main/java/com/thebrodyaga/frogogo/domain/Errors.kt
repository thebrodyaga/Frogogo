package com.thebrodyaga.frogogo.domain

import androidx.annotation.StringRes

data class ApiError(
        val errorCode: Int,
        @StringRes val errorMessageRes: Int? = null,
        val errorMessage: String? = null) : RuntimeException(errorMessage) {

    constructor(errorCode: Int, @StringRes errorMessageRes: Int? = null) :
            this(errorCode, errorMessageRes, null)

    constructor(errorCode: Int, errorMessage: String) :
            this(errorCode, null, errorMessage)
}