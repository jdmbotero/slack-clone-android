package com.sena.slackcloneandroid.model

class Data<T : Any>(type: String, attributes: T?) {

    var id: String? = null
    var type: String? = type
    var links: Link? = null
    var attributes: T? = attributes
    var relationships: HashMap<String, Relationship>? = null
}