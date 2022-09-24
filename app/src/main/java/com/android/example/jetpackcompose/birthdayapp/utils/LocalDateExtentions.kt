package com.android.example.jetpackcompose.birthdayapp.utils

/**
 * Created by Alvina Lushnikova on 23.09.22.
 */

fun String.cut(max: Int): String {
    if (this.length > max) {
        var name = this.substring(0, max)
        name += "..."
        return name
    } else {
        return this
    }
}