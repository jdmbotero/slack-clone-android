package com.sena.slackcloneandroid.model

class Json<T : Any> {

    constructor()
    constructor(data: Data<T>) {
        this.data = data
    }

    var data: Data<T>? = null
}