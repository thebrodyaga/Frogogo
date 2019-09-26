package com.thebrodyaga.frogogo.screen.adapters

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.thebrodyaga.frogogo.domain.net.UserResponse
import com.thebrodyaga.frogogo.screen.adapters.delegates.UserDelegate

class UserAdapter constructor(
    onListItemClick: (
        user: UserResponse,
        position: Int,
        sharedElements: List<Pair<View, String>>?
    ) -> Unit
) : AsyncListDifferDelegationAdapter<Any>(DiffCallback()) {

    init {
        delegatesManager.addDelegate(UserDelegate(onListItemClick))
    }

    class DiffCallback : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is UserResponse && newItem is UserResponse)
                return oldItem.id == newItem.id
            return false
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is UserResponse && newItem is UserResponse)
                return oldItem as UserResponse == newItem as UserResponse
            return false
        }

    }
}