package com.sena.slackcloneandroid.api.endpoint

import com.sena.slackcloneandroid.model.JsonArray
import com.sena.slackcloneandroid.model.JsonObject
import com.sena.slackcloneandroid.model.Subscription
import com.sena.slackcloneandroid.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface SubscriptionInterface {

    @Headers("Content-Type: application/vnd.api+json")
    @POST("subscriptions")
    fun post(@Body json: JsonObject<User>): Call<JsonObject<Subscription>>

    @Headers("Content-Type: application/vnd.api+json")
    @GET("subscriptions")
    fun get(): Call<JsonArray<Subscription>>

    @Headers("Content-Type: application/vnd.api+json")
    @GET("subscriptions/{id}")
    fun get(@Path("id") id: String): Call<JsonObject<Subscription>>

    @Headers("Content-Type: application/vnd.api+json")
    @PUT("subscriptions/{id}")
    fun put(@Path("id") id: String, @Body json: JsonObject<Subscription>): Call<JsonObject<Subscription>>

    @Headers("Content-Type: application/vnd.api+json")
    @DELETE("subscriptions/{id}")
    fun delete(@Path("id") id: String): Call<ResponseBody>
}