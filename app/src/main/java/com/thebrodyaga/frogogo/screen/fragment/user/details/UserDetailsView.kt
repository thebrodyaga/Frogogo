package com.thebrodyaga.frogogo.screen.fragment.user.details

import androidx.annotation.StringRes
import com.thebrodyaga.frogogo.domain.net.UserResponse
import com.thebrodyaga.frogogo.screen.common.CommonViewState
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface UserDetailsView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setState(state: CommonViewState)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setData(data: UserResponse)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun toggleButton(isVisible: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun errorName(@StringRes text:Int?)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun errorSurname(@StringRes text:Int?)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun errorEmail(@StringRes text:Int?)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showActionError(message: String)
}