package com.thebrodyaga.frogogo.screen.fragment.user.list

import com.thebrodyaga.frogogo.domain.net.UserResponse
import com.thebrodyaga.frogogo.screen.common.CommonViewState
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface UserListView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setState(state: CommonViewState)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setData(data: List<UserResponse>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showActionError(message: String)
}