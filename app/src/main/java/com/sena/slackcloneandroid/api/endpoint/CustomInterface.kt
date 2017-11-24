package com.sena.slackcloneandroid.api.endpoint

import com.sena.slackcloneandroid.model.Channel
import com.sena.slackcloneandroid.model.JsonArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface CustomInterface {

    @Headers("Content-Type: application/vnd.api+json")
    @GET()
    fun get(@Url url: String): Call<JsonArray<Channel>>
}