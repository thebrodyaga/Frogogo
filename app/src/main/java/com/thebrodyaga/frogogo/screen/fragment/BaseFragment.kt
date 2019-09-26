package com.thebrodyaga.frogogo.screen.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpAppCompatFragment

abstract class BaseFragment : MvpAppCompatFragment() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun unSubscribeOnDestroyView(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    open fun onBackPressed() {}

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.requestApplyInsets(view)
    }
}