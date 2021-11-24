package com.example.tasklist

import android.os.Build
import androidx.annotation.RequiresApi

class itemOnDis {
    @RequiresApi(Build.VERSION_CODES.O)
    fun encryptedToStr(str:String):String{
        val crypting = ChCrypto
        val taskCrypred = crypting.aesDecrypt(str)
        return taskCrypred
    }
}