package com.sena.slackcloneandroid.model

class Data<T : Any>(type: String, attributes: T) {

    var id: String? = null
    var type: String? = type
    var attributes: T? = attributes
}