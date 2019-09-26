package com.thebrodyaga.frogogo.frameworks.navigation

import androidx.fragment.app.Fragment
import com.thebrodyaga.frogogo.domain.net.UserResponse
import com.thebrodyaga.frogogo.screen.fragment.user.details.UserDetailsFragment
import com.thebrodyaga.frogogo.screen.fragment.user.list.UserListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    object UserListScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return UserListFragment()
        }
    }

    data class UserDetailsScreen(val user: UserResponse? = null) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return UserDetailsFragment.newInstance(user?.id)
        }
    }
}