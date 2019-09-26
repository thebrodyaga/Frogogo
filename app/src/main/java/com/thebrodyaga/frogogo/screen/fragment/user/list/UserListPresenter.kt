package com.thebrodyaga.frogogo.screen.fragment.user.list

import com.thebrodyaga.frogogo.di.Injector
import com.thebrodyaga.frogogo.domain.ErrorHandler
import com.thebrodyaga.frogogo.interactors.UserInteractor
import com.thebrodyaga.frogogo.screen.base.BasePresenter
import com.thebrodyaga.frogogo.screen.common.CommonViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class UserListPresenter @Inject constructor(
    private val interactor: UserInteractor,
    private val errorHandler: ErrorHandler
) : BasePresenter<UserListView>() {

    private var dataSubscribe: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData()
    }

    private fun loadData() {
        if (dataSubscribe?.isDisposed == false)
            return
        dataSubscribe = interactor
            .getUsers(true)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.setState(CommonViewState.PROGRESS) }
            .doOnError { viewState.setState(CommonViewState.ERROR) }
            .doOnSuccess {
                viewState.setState(
                    if (it.isEmpty()) CommonViewState.EMPTY
                    else CommonViewState.CONTENT
                )
            }
            .subscribe(
                { viewState.setData(it) },
                { errorHandler.proceed(it) { error -> viewState.showActionError(error) } })
    }

    override fun onDestroy() {
        super.onDestroy()
        dataSubscribe?.dispose()
        Injector.clearUserComponent()
    }
}