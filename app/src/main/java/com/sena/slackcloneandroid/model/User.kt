package com.sena.slackcloneandroid.model

import com.google.gson.annotations.SerializedName

class User {

    constructor()
    constructor(username: String, email: String, password: String, photoUrl: String) {
        this.username = username
        this.email = email
        this.password = password
        this.photoUrl = photoUrl
    }

    var username: String? = null
    var email: String? = null
    var password: String? = null

    @SerializedName("photo-url")
    var photoUrl: String? = null

}