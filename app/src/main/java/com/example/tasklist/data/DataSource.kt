package com.example.tasklist.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object DataSource {

    private var _tasks: ArrayList<Task>? = null
    val tasks: ArrayList<Task>
        @RequiresApi(Build.VERSION_CODES.O)
    get() {
        return _tasks!!
    }

    var zadanie = ArrayList<Task>()

}