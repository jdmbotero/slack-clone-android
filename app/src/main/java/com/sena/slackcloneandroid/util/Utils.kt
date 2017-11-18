package com.sena.slackcloneandroid.util

object Utils {

    fun getInitials(text: String): String {
        var initials = ""
        try {
            initials = text[0].toString()
            initials += if (text.split(" ".toRegex()).size > 1) {
                text.split(" ".toRegex())[1][0]
            } else {
                text.split(" ".toRegex())[0][1]
            }

            initials = initials.toUpperCase()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return initials
    }

}