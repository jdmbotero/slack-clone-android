package com.sena.slackcloneandroid

import android.app.Application
import com.sena.slackcloneandroid.api.ApiClient
import com.sena.slackcloneandroid.util.Preferences

class App : Application() {

    var preferences: Preferences? = null

    override fun onCreate() {
        super.onCreate()

        preferences = Preferences(applicationContext)
        ApiClient.baseUrl = getString(R.string.API_URL)
    }
}