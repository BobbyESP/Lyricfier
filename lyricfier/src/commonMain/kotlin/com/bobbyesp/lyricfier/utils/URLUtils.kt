package com.bobbyesp.lyricfier.utils

import io.ktor.utils.io.charsets.Charset
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray

object URLUtils {
    fun String.toEncodedUrl(charset: Charset = Charsets.UTF_8): String {
        val byteArray = this.toByteArray(charset)
        return byteArray.decodeToString()
    }
}