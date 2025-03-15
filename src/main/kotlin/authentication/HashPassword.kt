package com.example.authentication

import io.ktor.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

fun hashPassword(password: String): String {
    val hmac = Mac.getInstance("HmacSHA1")
    hmac.init(
        SecretKeySpec(
            System.getenv("HASH_SECRET_KEY").toByteArray(),
            "HmacSHA1"
        )
    )
    return hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))
}