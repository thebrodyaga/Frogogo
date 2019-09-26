package com.thebrodyaga.frogogo.screen

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.widget.ContentLoadingProgressBar

fun Context.toPx(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
}

fun Context.toPx(dp: Int): Int {
    return toPx(dp.toFloat()).toInt()
}

inline fun ViewGroup.forEach(action: (child: View) -> Unit) {
    for (chileIndex: Int in 0 until childCount)
        action.invoke(getChildAt(chileIndex))
}/*

fun View.systemWindowInsetTop() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        (v.layoutParams as ViewGroup.MarginLayoutParams).topMargin = insets.systemWindowInsetTop
        insets
    }
}

fun View.systemWindowInsetBottom() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        (v.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = insets.systemWindowInsetBottom
        insets
    }
}*/

fun View.isInvisible(isInvisible: Boolean) {
    this.visibility = if (isInvisible) View.INVISIBLE else View.VISIBLE
}

fun View.isGone(isGone: Boolean) {
    this.visibility = if (isGone) View.GONE else View.VISIBLE
}

fun ContentLoadingProgressBar.isShow(isShow: Boolean) {
    if (isShow) this.show()
    else this.hide()
}