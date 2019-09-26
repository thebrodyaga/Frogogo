package com.thebrodyaga.frogogo.screen.common

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

/**
 * @author Konstantin Tskhovrebov (aka terrakok). Date: 28.03.17
 */
class ResourceManager constructor(private val context: Context) {

    fun getString(@StringRes id: Int) = context.getString(id)

    fun getString(id: Int, vararg formatArgs: Any) = String.format(context.getString(id, *formatArgs))
}