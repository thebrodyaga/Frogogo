package com.thebrodyaga.frogogo.repository.impl

import com.thebrodyaga.frogogo.domain.net.UserResponse
import com.thebrodyaga.frogogo.domain.net.UserRequest
import com.thebrodyaga.frogogo.frameworks.net.FrogogoApi
import com.thebrodyaga.frogogo.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Single

class UserRepositoryImpl constructor(
    private val frogogoApi: FrogogoApi
) : UserRepository {

    private val usersList = mutableListOf<UserResponse>()

    override fun getUsers(upload: Boolean): Single<List<UserResponse>> {
        return if (!upload && usersList.isNotEmpty())
            Single.just(usersList)
        else frogogoApi.getUsers()
            .doOnSuccess {
                usersList.clear()
                usersList.addAll(it)
            }
    }

    override fun getUserById(id: Int): Single<UserResponse> {
        val user = usersList.firstOrNull { it.id == id }
        return if (user != null)
            Single.just(user)
        else frogogoApi.getUserById(id)
    }

    override fun createUser(body: UserRequest): Completable {
        return frogogoApi.createUser(body)
    }

    override fun updateUser(id: Int, body: UserRequest): Completable {
        return frogogoApi.updateUser(id, body)
    }
}