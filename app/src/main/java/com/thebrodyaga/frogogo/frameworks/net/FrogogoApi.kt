package com.thebrodyaga.frogogo.frameworks.net

import com.thebrodyaga.frogogo.domain.net.UserResponse
import com.thebrodyaga.frogogo.domain.net.UserRequest
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface FrogogoApi {
    @GET("/users.json")
    fun getUsers(): Single<List<UserResponse>>

    @GET("/users/{id}.json")
    fun getUserById(@Path(value = "id") id: Int): Single<UserResponse>

    @POST("/users.json")
    fun createUser(@Body body: UserRequest): Completable

    @PATCH("/users/{id}.json")
    fun updateUser(@Path(value = "id") id: Int, @Body body: UserRequest): Completable
}