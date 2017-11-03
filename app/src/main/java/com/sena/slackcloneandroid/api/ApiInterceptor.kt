package com.sena.slackcloneandroid.api

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val original = chain!!.request()
        val request = original.newBuilder()
                .header("Content-Type", "application/vnd.api+json")
                .method(original.method(), original.body())
                .build()
        return chain.proceed(request)
    }
}