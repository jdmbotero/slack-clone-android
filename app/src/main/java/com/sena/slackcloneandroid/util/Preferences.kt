package com.sena.slackcloneandroid.util

import android.content.Context
import com.sena.slackcloneandroid.model.User

class Preferences(private val context: Context) {

    fun setUser(user: User?) {
        val sharedPref = context.getSharedPreferences(context.packageName + ".preferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("user", user!!.toString())
        editor.apply()
    }

    fun getUser(): User {
        val sharedPref = context.getSharedPreferences(context.packageName + ".preferences", Context.MODE_PRIVATE)
        return User.fromString(sharedPref.getString("user", ""))
    }

}