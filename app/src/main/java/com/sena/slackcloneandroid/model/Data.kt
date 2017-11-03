package com.sena.slackcloneandroid.model

class Data<T : Any> {

    constructor()
    constructor(type: String, attributes: T) {
        this.type = type
        this.attributes = attributes
    }

    var type: String? = null
    var id: String? = null
    var attributes: T? = null

}