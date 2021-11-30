package com.example.tasklist

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class Cryption {
    @SuppressLint("GetInstance")
    @RequiresApi(Build.VERSION_CODES.O)
    fun decrypt(cipherText: String, key: SecretKeySpec, iv: IvParameterSpec): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, key, iv)
        val plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText))
        return String(plainText)
    }

    @SuppressLint("GetInstance")
    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(inputText: String, key: SecretKeySpec, iv: IvParameterSpec): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)
        val cipherText = cipher.doFinal(inputText.toByteArray())
        return Base64.getEncoder().encodeToString(cipherText)
    }

//    val inputText = "abcdefghigklmnopqrstuvwxyz0123456789"
//    val algorithm = "AES/CBC/PKCS5Padding"
//    val key = SecretKeySpec("1234567890123456".toByteArray(), "AES")
//    val iv = IvParameterSpec(ByteArray(16))
}