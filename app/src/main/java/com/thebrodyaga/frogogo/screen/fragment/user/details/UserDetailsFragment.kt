package com.thebrodyaga.frogogo.screen.fragment.user.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.jakewharton.rxbinding3.widget.textChanges

import com.thebrodyaga.frogogo.R
import com.thebrodyaga.frogogo.di.Injector
import com.thebrodyaga.frogogo.domain.net.UserRequest
import com.thebrodyaga.frogogo.domain.net.UserResponse
import com.thebrodyaga.frogogo.screen.common.CommonViewState
import com.thebrodyaga.frogogo.screen.common.StubViewHolder
import com.thebrodyaga.frogogo.screen.fragment.BaseFragment
import com.thebrodyaga.frogogo.screen.isInvisible
import com.thebrodyaga.frogogo.screen.toPx
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function3
import kotlinx.android.synthetic.main.fragment_user_details.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UserDetailsFragment : BaseFragment(), UserDetailsView {
    override fun showActionError(message: String) {
        Toast.makeText(context ?: return, message, Toast.LENGTH_SHORT).show()
    }

    override fun errorName(text: Int?) {
        setErrorInTextLayout(name_text_layout, text)
    }

    override fun errorSurname(text: Int?) {
        setErrorInTextLayout(surname_text_layout, text)
    }

    override fun errorEmail(text: Int?) {
        setErrorInTextLayout(mail_text_layout, text)
    }

    private fun setErrorInTextLayout(textInputLayout: TextInputLayout, text: Int?) {
        text?.also { textInputLayout.error = resources.getString(it) }
            ?: run { textInputLayout.error = "" }
    }

    override fun toggleButton(isVisible: Boolean) {
        save_button.isInvisible(!isVisible)
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: UserDetailsPresenter

    @ProvidePresenter
    fun providePresenter() = presenter.also { it.id = id }

    private var stubViewHolder: StubViewHolder? = null
    private val id: Int?
        get() = arguments?.getInt(EXTRA_ID_FLAG)
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.plusUserComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_user_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id?.run { ViewCompat.setTransitionName(root_view, id.toString()) }
        stubViewHolder = StubViewHolder(stub_view)
        ViewCompat.setOnApplyWindowInsetsListener(root_view) { _, insets ->
            val toolbarLayoutParams = toolbar.layoutParams as ViewGroup.MarginLayoutParams
            toolbarLayoutParams.topMargin = insets.systemWindowInsetTop
            insets.consumeSystemWindowInsets()
        }
        save_button.setOnClickListener {
            presenter.onApplyClick(
                UserRequest(
                    name_text_field.text?.toString() ?: "",
                    surname_text_field.text?.toString() ?: "",
                    mail_text_field.text?.toString() ?: "",
                    ""
                )
            )
        }
        disposable?.dispose()
        fun getWatcher(editText: EditText) = editText
            .textChanges()
            .debounce(200, TimeUnit.MILLISECONDS)
            .map { it.toString() }
            .observeOn(AndroidSchedulers.mainThread())
        disposable = Observable.combineLatest(
            getWatcher(name_text_field).doOnNext { presenter.onNewName(it) },
            getWatcher(surname_text_field).doOnNext { presenter.onNewSurname(it) },
            getWatcher(mail_text_field).doOnNext { presenter.onNewMail(it) },
            Function3<String, String, String, UserRequest> { name, surname, mail ->
                UserRequest(
                    name,
                    surname,
                    mail,
                    ""
                )
            }
        ).skip(1).subscribe { presenter.onNewData(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.dispose()
    }

    override fun setData(data: UserResponse) {
        ViewCompat.setTransitionName(root_view, data.id.toString())
        name_text_field.setText(data.firstName, TextView.BufferType.EDITABLE)
        surname_text_field.setText(data.lastName, TextView.BufferType.EDITABLE)
        mail_text_field.setText(data.email, TextView.BufferType.EDITABLE)
        toolbar.title = data.firstName.plus(" ").plus(data.lastName)
        Glide.with(avatar_icon.context)
            .load(data.avatarUrl)
            .error(R.drawable.ic_account_circle)
            .into(avatar_icon)
        Glide.with(imageView.context)
            .load(data.avatarUrl)
            .error(R.drawable.ic_account_circle)
            .into(imageView)
    }

    override fun setState(state: CommonViewState) {
        scrollable_layout.isInvisible(state != CommonViewState.CONTENT)
        stubViewHolder?.setState(
            when (state) {
                CommonViewState.CONTENT -> StubViewHolder.ViewState.HIDE
                CommonViewState.PROGRESS -> StubViewHolder.ViewState.PROGRESS
                CommonViewState.EMPTY -> StubViewHolder.ViewState.EMPTY
                CommonViewState.ERROR -> StubViewHolder.ViewState.ERROR
            }
        )
    }

    companion object {
        private const val EXTRA_ID_FLAG = "EXTRA_ID_FLAG"
        fun newInstance(
            id: Int?
        ): UserDetailsFragment =
            UserDetailsFragment().also {
                it.arguments = Bundle()
                    .apply { id?.also { this.putInt(EXTRA_ID_FLAG, id) } }
            }
    }
}
