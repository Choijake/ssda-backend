package com.boseyo.backend.util

import java.security.MessageDigest

class MD5Generator(input: String) {
    private val result: String

    init {
        val mdMD5 = MessageDigest.getInstance("MD5")
        mdMD5.update(input.toByteArray(charset("UTF-8")))
        val md5Hash = mdMD5.digest()
        val hexMD5hash = StringBuilder()
        for (b in md5Hash) {
            val hexString = String.format("%02x", b)
            hexMD5hash.append(hexString)
        }
        result = hexMD5hash.toString()
    }

    override fun toString(): String {
        return result
    }
}