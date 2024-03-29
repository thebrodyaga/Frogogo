package com.thebrodyaga.frogogo.frameworks.navigation.animation

import android.content.Context
import android.util.AttributeSet
import androidx.transition.ChangeBounds
import androidx.transition.ChangeTransform
import androidx.transition.TransitionSet

class ExpandTransition : TransitionSet {
    constructor() {
        init()
    }

    /**
     * This constructor allows us to use this transition in XML
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        setOrdering(ORDERING_TOGETHER)
        addTransition(ChangeBounds())
            .addTransition(ChangeTransform())
    }
}