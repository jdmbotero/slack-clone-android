package com.sena.slackcloneandroid.util

import android.content.Context
import com.sena.slackcloneandroid.model.User

class Preferences(private val context: Context) {

    private val user_key = "user"

    fun setUser(user: User?) {
        val sharedPref = context.getSharedPreferences(context.packageName + ".preferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(user_key, user!!.toString())
        editor.apply()
    }

    fun getUser(): User? {
        val sharedPref = context.getSharedPreferences(context.packageName + ".preferences", Context.MODE_PRIVATE)
        return User.fromString(sharedPref.getString(user_key, ""))
    }

}