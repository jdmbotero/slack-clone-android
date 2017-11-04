package com.sena.slackcloneandroid.api.endpoint

import com.sena.slackcloneandroid.model.Json
import com.sena.slackcloneandroid.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserInterface {

    @Headers("Content-Type: application/vnd.api+json")
    @POST("users")
    fun post(@Body json: Json<User>): Call<Json<User>>

    @Headers("Content-Type: application/vnd.api+json")
    @GET("users")
    fun get(@Body json: Json<User>): Call<Json<User>>

    @Headers("Content-Type: application/vnd.api+json")
    @PUT("users/{id}")
    fun put(@Path("id") id: String, @Body json: Json<User>): Call<Json<User>>

    @Headers("Content-Type: application/vnd.api+json")
    @DELETE("users/{id}")
    fun delete(): Call<ResponseBody>
}