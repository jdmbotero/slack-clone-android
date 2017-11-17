package com.sena.slackcloneandroid.activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.sena.slackcloneandroid.R

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        delay()
    }

    private fun delay() {
        Handler().postDelayed({
            startActivity(LoginActivity.newIntent(this))
            finish()
        }, 1800)
    }
}
