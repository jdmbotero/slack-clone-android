package com.sena.slackcloneandroid.model

import com.google.gson.annotations.SerializedName

class User {

    var username: String? = null
    var email: String? = null
    var password: String? = null

    @SerializedName("photo-url")
    var photo_url: String? = null

}