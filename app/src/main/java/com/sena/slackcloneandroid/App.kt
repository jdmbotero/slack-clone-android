package com.sena.slackcloneandroid

import android.app.Application
import com.sena.slackcloneandroid.api.ApiClient

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ApiClient.baseUrl = getString(R.string.API_URL)
    }
}