package com.sena.slackcloneandroid.api.endpoint

import com.sena.slackcloneandroid.model.Channel
import com.sena.slackcloneandroid.model.JsonArray
import com.sena.slackcloneandroid.model.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ChannelInterface {

    @Headers("Content-Type: application/vnd.api+json")
    @POST("channels")
    fun post(@Body json: JsonObject<Channel>): Call<JsonObject<Channel>>

    @Headers("Content-Type: application/vnd.api+json")
    @GET("channels")
    fun get(): Call<JsonArray<Channel>>

    @Headers("Content-Type: application/vnd.api+json")
    @GET("channels")
    fun search(@Query("filter[name]") name: String): Call<JsonArray<Channel>>

    @Headers("Content-Type: application/vnd.api+json")
    @GET("channels/{id}")
    fun get(@Path("id") id: String): Call<JsonObject<Channel>>

    @Headers("Content-Type: application/vnd.api+json")
    @PUT("channels/{id}")
    fun put(@Path("id") id: String, @Body json: JsonObject<Channel>): Call<JsonObject<Channel>>

    @Headers("Content-Type: application/vnd.api+json")
    @DELETE("channels/{id}")
    fun delete(@Path("id") id: String): Call<ResponseBody>
}