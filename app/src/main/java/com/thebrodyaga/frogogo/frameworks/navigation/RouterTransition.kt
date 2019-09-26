package com.thebrodyaga.frogogo.frameworks.navigation

import android.view.View
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen

class RouterTransition : Router() {

    fun navigateToWithTransition(
        screen: Screen,
        transitionBox: TransitionBox,
        sharedElement: List<Pair<View, String>>
    ) {
        executeCommands(ForwardWithTransition(screen, transitionBox,  sharedElement))
    }

    fun replaceWithTransition(
        screen: Screen,
        transitionBox: TransitionBox,
        sharedElement: List<Pair<View, String>>
    ) {
        executeCommands(ReplaceWithTransition(screen, transitionBox, sharedElement))
    }
}
