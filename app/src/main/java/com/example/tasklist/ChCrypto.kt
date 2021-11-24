package com.example.tasklist

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object ChCrypto{
    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic fun aesEncrypt(v:String) = AES256.encrypt(v)
    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic fun aesDecrypt(v:String) = AES256.decrypt(v)
}

private object AES256{
    @RequiresApi(Build.VERSION_CODES.O)
    private val encorder = Base64.getEncoder()
    @RequiresApi(Build.VERSION_CODES.O)
    private val decorder = Base64.getDecoder()
    private fun cipher(opmode:Int): Cipher {
        val secretKey = "H+MbQeThWmZq4t7w!z%C&F)J@NcRfUjX"
        if(secretKey.length != 32) throw RuntimeException("SecretKey length is not 32 chars")
        val c = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val sk = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "AES")
        val iv = IvParameterSpec(secretKey.substring(0, 16).toByteArray(Charsets.UTF_8))
        c.init(opmode, sk, iv)
        return c
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(str:String):String{
        val encrypted = cipher(Cipher.ENCRYPT_MODE).doFinal(str.toByteArray(Charsets.UTF_8))
        return String(encorder.encode(encrypted))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decrypt(str:String):String{
        val byteStr = decorder.decode(str.toByteArray(Charsets.UTF_8))
        return String(cipher(Cipher.DECRYPT_MODE).doFinal(byteStr))
    }


}