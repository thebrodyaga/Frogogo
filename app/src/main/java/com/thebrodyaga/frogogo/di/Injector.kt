package com.thebrodyaga.frogogo.di

import com.thebrodyaga.frogogo.App
import com.thebrodyaga.frogogo.di.user.UserComponent

object Injector {

    private var userComponent: UserComponent? = null
    fun plusUserComponent(): UserComponent {
        if (userComponent == null)
            userComponent = App.INSTANCE.appComponent.plusUserComponent()
        return userComponent as UserComponent
    }

    fun clearUserComponent() {
        userComponent = null
    }

}