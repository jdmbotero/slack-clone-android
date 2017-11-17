package com.sena.slackcloneandroid.model

import com.google.gson.Gson
import com.squareup.moshi.Json

class User {

    constructor(username: String, email: String, password: String, photoUrl: String) {
        this.username = username
        this.email = email
        this.password = password
        this.photoUrl = photoUrl
    }

    constructor(username: String, password: String)  {
        this.username = username
        this.password = password
    }

    var id: String? = null
    var username: String? = null
    var email: String? = null
    var password: String? = null

    @Json(name = "photo-url")
    var photoUrl: String? = null

    var token: String? = null

    override fun toString(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun fromString(user: String): User {
            return Gson().fromJson<User>(user, User::class.java)
        }
    }
}