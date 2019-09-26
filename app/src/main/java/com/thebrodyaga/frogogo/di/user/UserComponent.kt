package com.thebrodyaga.frogogo.di.user

import com.thebrodyaga.frogogo.screen.fragment.user.details.UserDetailsFragment
import com.thebrodyaga.frogogo.screen.fragment.user.list.UserListFragment
import dagger.Subcomponent
import ru.yandex.waybill.combine.di.FirstScope

@FirstScope
@Subcomponent(modules = [UserModule::class])
interface UserComponent {
    fun inject(userListFragment: UserListFragment)
    fun inject(userListFragment: UserDetailsFragment)
}