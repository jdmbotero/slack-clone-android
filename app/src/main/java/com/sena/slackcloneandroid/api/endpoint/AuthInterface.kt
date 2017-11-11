package com.sena.slackcloneandroid.api.endpoint

import com.sena.slackcloneandroid.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthInterface {

    @Headers("Content-Type: application/vnd.api+json")
    @POST("login")
    fun login(@Body user: User): Call<User>
}