package com.sena.slackcloneandroid.model

class User {

    constructor()
    constructor(username: String, email: String, password: String, photo: String) {
        this.username = username
        this.email = email
        this.password = password
        this.photo = photo
    }

    var username: String? = null
    var email: String? = null
    var password: String? = null
    var photo: String? = null

}