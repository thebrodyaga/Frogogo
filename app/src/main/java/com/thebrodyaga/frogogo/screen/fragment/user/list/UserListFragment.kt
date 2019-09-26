package com.thebrodyaga.frogogo.screen.fragment.user.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade

import com.thebrodyaga.frogogo.R
import com.thebrodyaga.frogogo.di.Injector
import com.thebrodyaga.frogogo.domain.net.UserResponse
import com.thebrodyaga.frogogo.frameworks.navigation.RouterTransition
import com.thebrodyaga.frogogo.frameworks.navigation.Screens
import com.thebrodyaga.frogogo.frameworks.navigation.TransitionBox
import com.thebrodyaga.frogogo.frameworks.navigation.animation.ExpandTransition
import com.thebrodyaga.frogogo.screen.adapters.UserAdapter
import com.thebrodyaga.frogogo.screen.common.CommonViewState
import com.thebrodyaga.frogogo.screen.common.StubViewHolder
import com.thebrodyaga.frogogo.screen.fragment.BaseFragment
import com.thebrodyaga.frogogo.screen.isInvisible
import kotlinx.android.synthetic.main.fragment_user_list.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import androidx.core.view.ViewCompat

class UserListFragment : BaseFragment(), UserListView {
    override fun showActionError(message: String) {
        Toast.makeText(context ?: return, message, Toast.LENGTH_SHORT).show()
    }

    @Inject
    lateinit var router: RouterTransition

    @Inject
    @InjectPresenter
    lateinit var presenter: UserListPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private var stubViewHolder: StubViewHolder? = null
    private val adapter = UserAdapter(
        onListItemClick = { user, _, sharedElements ->
            if (sharedElements != null)
                router.navigateToWithTransition(
                    Screens.UserDetailsScreen(user),
                    TransitionBox(
                        ExpandTransition(),
                        Fade(),
                        Fade(),
                        ExpandTransition()
                    ),
                    sharedElements
                )
            else router.navigateTo(Screens.UserDetailsScreen(user))
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.plusUserComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_user_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stubViewHolder = StubViewHolder(stub_view)
        list.apply {
            adapter = this@UserListFragment.adapter
            layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
        ViewCompat.setOnApplyWindowInsetsListener(root_view) { _, insets ->
            val toolbarLayoutParams = toolbar.layoutParams as ViewGroup.MarginLayoutParams
            toolbarLayoutParams.topMargin = insets.systemWindowInsetTop
            insets.consumeSystemWindowInsets()
        }
        add_new.setOnClickListener { router.navigateTo(Screens.UserDetailsScreen()) }
    }

    override fun setData(data: List<UserResponse>) {
        adapter.items = data
    }

    override fun setState(state: CommonViewState) {
        list.isInvisible(state != CommonViewState.CONTENT)
        add_new.isInvisible(state != CommonViewState.CONTENT)
        stubViewHolder?.setState(
            when (state) {
                CommonViewState.CONTENT -> StubViewHolder.ViewState.HIDE
                CommonViewState.PROGRESS -> StubViewHolder.ViewState.PROGRESS
                CommonViewState.EMPTY -> StubViewHolder.ViewState.EMPTY
                CommonViewState.ERROR -> StubViewHolder.ViewState.ERROR
            }
        )
    }

}
