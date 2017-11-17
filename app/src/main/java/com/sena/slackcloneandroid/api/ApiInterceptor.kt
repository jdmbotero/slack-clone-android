package com.sena.slackcloneandroid.api

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {

    companion object {
        var token: String = ""
    }

    override fun intercept(chain: Interceptor.Chain?): Response {
        val original = chain!!.request()
        val request = original.newBuilder()
                .header("Content-Type", "application/vnd.api+json")
                .header("Authorization", token)
                .method(original.method(), original.body())
                .build()
        return chain.proceed(request)
    }
}