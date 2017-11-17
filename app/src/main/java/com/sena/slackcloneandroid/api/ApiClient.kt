package com.sena.slackcloneandroid.api

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient {

    var baseUrl: String = ""
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        if (retrofit == null) {

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                    .addInterceptor(ApiInterceptor())
                    .addInterceptor(loggingInterceptor).build()

            val moshi = Moshi.Builder().build()
            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .client(client)
                    .build()
        }
        return retrofit
    }

    fun getClient(token: String): Retrofit? {
        ApiInterceptor.token = token
        return getClient()
    }

}