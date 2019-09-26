package com.thebrodyaga.frogogo.interactors

import com.thebrodyaga.frogogo.domain.net.UserRequest
import com.thebrodyaga.frogogo.repository.UserRepository

class UserInteractor constructor(private val userRepository: UserRepository) {

    fun getUsers(upload: Boolean = false) = userRepository.getUsers(upload)
    fun getUserById(id: Int) = userRepository.getUserById(id)
    fun createUser(body: UserRequest) = userRepository.createUser(body)
    fun updateUser(id: Int, body: UserRequest) = userRepository.updateUser(id, body)
}