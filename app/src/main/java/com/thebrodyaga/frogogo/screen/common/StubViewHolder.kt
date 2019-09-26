package com.thebrodyaga.frogogo.screen.common

import android.view.View
import androidx.annotation.StringRes
import com.thebrodyaga.frogogo.R
import com.thebrodyaga.frogogo.screen.isInvisible
import com.thebrodyaga.frogogo.screen.isShow
import kotlinx.android.synthetic.main.include_stub_view.view.*

class StubViewHolder constructor(val rootView: View) {
    var state = ViewState.HIDE
        private set

    private var errorText: Pair<Int, Int> = Pair(R.string.error_loading, R.string.retry)
    private var emptyText: Pair<Int, Int> = Pair(R.string.empty_list, R.string.retry)

    private fun setErrorText(@StringRes text: Int, @StringRes button: Int) {
        errorText = Pair(text, button)
    }

    private fun setEmptyText(@StringRes text: Int, @StringRes button: Int) {
        emptyText = Pair(text, button)
    }

    fun showProgress() {
        setState(ViewState.PROGRESS)
    }

    fun showError() {
        setState(ViewState.ERROR)
        with(rootView) {
            text.setText(errorText.first)
            button.setText(errorText.second)
        }
    }

    fun showEmpty() {
        setState(ViewState.EMPTY)
        with(rootView) {
            text.setText(emptyText.first)
            button.setText(emptyText.second)
        }
    }

    fun setState(viewState: ViewState): Unit = with(rootView) {
        when (viewState) {
            ViewState.PROGRESS -> {
                linear_layout.isInvisible(true)
                progress_bar.isShow(true)
            }
            ViewState.ERROR, ViewState.EMPTY -> {
                linear_layout.isInvisible(false)
                progress_bar.isShow(false)
            }
            ViewState.HIDE -> {
                linear_layout.isInvisible(true)
                progress_bar.isShow(false)
            }
        }
    }

    enum class ViewState {
        PROGRESS, ERROR, EMPTY, HIDE
    }
}