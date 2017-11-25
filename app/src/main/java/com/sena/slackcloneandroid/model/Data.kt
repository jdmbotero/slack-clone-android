package com.sena.slackcloneandroid.model

import java.io.Serializable

class Data<T : Any>(type: String, attributes: T?) : Serializable {

    var id: String? = null
    var type: String? = type
    var links: Link? = null
    var attributes: T? = attributes
    var relationships: HashMap<String, Relationship>? = null
}