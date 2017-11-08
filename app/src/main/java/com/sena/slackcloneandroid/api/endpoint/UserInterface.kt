package com.sena.slackcloneandroid.api.endpoint

import com.sena.slackcloneandroid.model.JsonArray
import com.sena.slackcloneandroid.model.JsonObject
import com.sena.slackcloneandroid.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserInterface {

    @Headers("Content-Type: application/vnd.api+json")
    @POST("users")
    fun post(@Body json: JsonObject<User>): Call<JsonObject<User>>

    @Headers("Content-Type: application/vnd.api+json")
    @GET("users")
    fun get(): Call<JsonArray<User>>

    @Headers("Content-Type: application/vnd.api+json")
    @PUT("users/{id}")
    fun put(@Path("id") id: String, @Body json: JsonObject<User>): Call<JsonObject<User>>

    @Headers("Content-Type: application/vnd.api+json")
    @DELETE("users/{id}")
    fun delete(@Path("id") id: String): Call<ResponseBody>
}