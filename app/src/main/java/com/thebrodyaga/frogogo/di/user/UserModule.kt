package com.thebrodyaga.frogogo.di.user

import com.thebrodyaga.frogogo.frameworks.net.FrogogoApi
import com.thebrodyaga.frogogo.interactors.UserInteractor
import com.thebrodyaga.frogogo.repository.UserRepository
import com.thebrodyaga.frogogo.repository.impl.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import ru.yandex.waybill.combine.di.FirstScope

@Module
class UserModule {

    @FirstScope
    @Provides
    fun provideInteractor(userRepository: UserRepository) = UserInteractor(userRepository)

    @FirstScope
    @Provides
    fun provideRepository(frogogoApi: FrogogoApi):UserRepository = UserRepositoryImpl(frogogoApi)
}