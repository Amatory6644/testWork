package com.example.testwork.data.security

import java.security.MessageDigest

object PasswordHasher {
    fun hash(password: String): String{
        val digest = MessageDigest.getInstance("SHA-256")
        val hashByte =  digest.digest(password.toByteArray(Charsets.UTF_8))
        return  hashByte.joinToString("") {"%02x".format(it)}
    }
}