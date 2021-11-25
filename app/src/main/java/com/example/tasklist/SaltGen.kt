package com.example.tasklist

import java.math.BigInteger
import java.security.SecureRandom


    private fun generateSalt(): ByteArray{
        val sr = SecureRandom()
        val salt = ByteArray(32)
        sr.nextBytes(salt)
        println("MOJ SALT{${toHex(salt)}}:")
        return salt
    }


    private fun toHex(array: ByteArray): String {
        val bi = BigInteger(1, array)
        val hex = bi.toString(16)
        val paddingLength = array.size * 2 - hex.length
        return if (paddingLength > 0) {
            String.format("%0" + paddingLength + "d", 0) + hex
        } else {
            hex
        }
    }