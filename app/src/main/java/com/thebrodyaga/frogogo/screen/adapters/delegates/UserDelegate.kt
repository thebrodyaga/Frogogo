package com.thebrodyaga.frogogo.screen.adapters.delegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.thebrodyaga.frogogo.R
import com.thebrodyaga.frogogo.domain.net.UserResponse
import kotlinx.android.synthetic.main.item_user.view.*

class UserDelegate constructor(
    private val onListItemClick: (
        user: UserResponse, position: Int,
        sharedElements: List<Pair<View, String>>?
    ) -> Unit
) : AbsListItemAdapterDelegate<UserResponse, Any, UserDelegate.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)
        )
    }

    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean {
        return item is UserResponse
    }

    override fun onBindViewHolder(
        item: UserResponse,
        holder: ViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item: UserResponse? = null

        init {
            itemView.setOnClickListener {
                item?.apply {
                    onListItemClick.invoke(this, adapterPosition, null)
                }
            }
        }

        fun bind(item: UserResponse) = with(itemView) {
            this@ViewHolder.item = item
            ViewCompat.setTransitionName(root_view, item.id.toString())
            first_line.text = item.firstName.plus(" ").plus(item.lastName)
            second_line.text = item.email
            Glide.with(icon.context)
                .load(item.avatarUrl)
                .error(R.drawable.ic_account_circle)
                .into(icon)
        }
    }
}