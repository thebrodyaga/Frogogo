package com.thebrodyaga.frogogo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.view.forEach
import com.thebrodyaga.frogogo.frameworks.navigation.RouterTransition
import com.thebrodyaga.frogogo.frameworks.navigation.Screens
import com.thebrodyaga.frogogo.frameworks.navigation.TransitionNavigator
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var router: RouterTransition
    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    private val navigator = TransitionNavigator(this, supportFragmentManager, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        App.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            router.newRootScreen(Screens.UserListScreen)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        router.exit()
    }
}
