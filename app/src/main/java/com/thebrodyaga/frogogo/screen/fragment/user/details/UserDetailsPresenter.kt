package com.thebrodyaga.frogogo.screen.fragment.user.details

import com.thebrodyaga.frogogo.R
import com.thebrodyaga.frogogo.domain.ErrorHandler
import com.thebrodyaga.frogogo.domain.net.UserResponse
import com.thebrodyaga.frogogo.domain.net.UserRequest
import com.thebrodyaga.frogogo.frameworks.navigation.Screens
import com.thebrodyaga.frogogo.interactors.UserInteractor
import com.thebrodyaga.frogogo.screen.base.BasePresenter
import com.thebrodyaga.frogogo.screen.common.CommonViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import java.util.regex.Pattern
import javax.inject.Inject

@InjectViewState
class UserDetailsPresenter @Inject constructor(
    private val interactor: UserInteractor,
    private val router: Router,
    private val errorHandler: ErrorHandler
) : BasePresenter<UserDetailsView>() {
    var id: Int? = null
    private var dataSubscribe: Disposable? = null
    private var userSubscribe: Disposable? = null
    private var baseExistingUser: UserResponse? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (!isCreateMode()) loadData(id)
        else viewState.setState(CommonViewState.CONTENT)
        viewState.toggleButton(isCreateMode())
    }

    private fun loadData(id: Int?) {
        if (id == null || dataSubscribe?.isDisposed == false)
            return
        dataSubscribe = interactor
            .getUserById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.setState(CommonViewState.PROGRESS) }
            .doOnError { viewState.setState(CommonViewState.ERROR) }
            .doOnSuccess { viewState.setState(CommonViewState.CONTENT) }
            .subscribe({
                baseExistingUser = it
                viewState.setData(it)
            }, { errorHandler.proceed(it) { error -> viewState.showActionError(error) } })
    }

    fun onNewName(text: String) {
        viewState.errorName(null)
    }

    fun onNewSurname(text: String) {
        viewState.errorSurname(null)
    }

    fun onNewMail(text: String) {
        viewState.errorEmail(null)
    }

    fun onNewData(user: UserRequest) {
        viewState.toggleButton(
            if (isCreateMode()) {
                user.email.isNotBlank() ||
                        user.firstName.isNotBlank() ||
                        user.lastName.isNotBlank()
            } else baseExistingUser
                ?.equalsBy(user.firstName, user.lastName, user.email)?.not() ?: false
        )
    }

    fun onApplyClick(user: UserRequest) {
        if (!validateUser(user))
            return
        if (isCreateMode())
            createUser(user)
        else updateUser(user)
    }

    private fun updateUser(user: UserRequest) {
        if (userSubscribe?.isDisposed == false)
            return
        userSubscribe = interactor.updateUser(id ?: return, user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.setState(CommonViewState.PROGRESS) }
            .doOnError { viewState.setState(CommonViewState.CONTENT) }
            .subscribe(
                { router.newRootScreen(Screens.UserListScreen) },
                { errorHandler.proceed(it) { error -> viewState.showActionError(error) } })
    }

    private fun createUser(user: UserRequest) {
        if (userSubscribe?.isDisposed == false)
            return
        userSubscribe = interactor.createUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.setState(CommonViewState.PROGRESS) }
            .doOnError { viewState.setState(CommonViewState.CONTENT) }
            .subscribe(
                { router.newRootScreen(Screens.UserListScreen) },
                { errorHandler.proceed(it) { error -> viewState.showActionError(error) } })
    }

    private fun validateUser(user: UserRequest): Boolean {
        var result = true
        if (user.firstName.isBlank()) {
            viewState.errorName(R.string.error_name)
            result = false
        } else viewState.errorName(null)
        if (user.lastName.isBlank()) {
            viewState.errorSurname(R.string.error_surname)
            result = false
        } else viewState.errorSurname(null)
        if (!validateEmail(user.email)) {
            viewState.errorEmail(R.string.error_email)
            result = false
        } else viewState.errorEmail(null)
        return result
    }

    private fun isCreateMode() = id == null || id == 0

    override fun onDestroy() {
        super.onDestroy()
        dataSubscribe?.dispose()
    }

    private fun validateEmail(email: String?): Boolean {
        return email != null && EMAIL_ADDRESS.matcher(email).matches()
    }

    companion object {
        private val EMAIL_ADDRESS = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
        )
    }
}