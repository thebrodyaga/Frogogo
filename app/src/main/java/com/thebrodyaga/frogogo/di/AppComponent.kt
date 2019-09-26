package com.thebrodyaga.frogogo.di

import android.app.Application
import com.thebrodyaga.frogogo.MainActivity
import com.thebrodyaga.frogogo.di.modules.NavigationModule
import com.thebrodyaga.frogogo.di.modules.NetworkModule
import com.thebrodyaga.frogogo.di.user.UserComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NavigationModule::class, NetworkModule::class])
interface AppComponent {

    fun plusUserComponent(): UserComponent
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}