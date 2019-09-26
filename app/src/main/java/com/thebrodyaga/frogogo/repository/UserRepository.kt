package com.thebrodyaga.frogogo.repository

import com.thebrodyaga.frogogo.domain.net.UserResponse
import com.thebrodyaga.frogogo.domain.net.UserRequest
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {
    fun getUsers(upload: Boolean = false): Single<List<UserResponse>>
    fun getUserById(id: Int): Single<UserResponse>
    fun createUser(body: UserRequest): Completable
    fun updateUser(id: Int, body: UserRequest): Completable
}