package com.greydog.fitness.repository.remote

import com.greydog.datamodels.*
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FitnessApiEndpoints {

    @POST("sessions/new")
    fun postLogin(@Body newUserRequest: NewUserRequest): Deferred<NewUserResponse>

    @GET("users/{userid}")
    fun getUser(@Path("userid") userid: Int, @Body request: User): Deferred<User>

    @POST("users/{userid}/steps")
    fun postSteps(@Path("userid") userid: Int, @Body request: Steps): Deferred<Steps>
}