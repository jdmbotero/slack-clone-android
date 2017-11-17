package com.sena.slackcloneandroid.model

class Channel {

    constructor(name: String, description: String, private: Boolean) {
        this.name = name
        this.description = description
        this.private = private
    }

    var id: String? = null
    var name: String? = null
    var description: String? = null
    var private: Boolean? = null

}