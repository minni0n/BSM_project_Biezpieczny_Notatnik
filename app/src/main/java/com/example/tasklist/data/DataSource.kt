package com.example.tasklist.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object DataSource {

    private var listOfTasks: Array<String> = arrayOf("Zrobic zakupy",
        "Zrobic zadanie domowe", "Pojsc na spacer",
        "Posprzataс w domu", "Pomoc bratu", "Pospacerowac z psem", "Odebrac brata ze szkoly",
        "Kupic laptop", "Zadzwonic do przyjaciol", "Zaspiewac piosenke")
    private var _tasks: ArrayList<Task>? = null
    val tasks: ArrayList<Task>
        @RequiresApi(Build.VERSION_CODES.O)
    get() {
        if(_tasks == null) {
            MockTasks()
        }
        return _tasks!!
    }

    var zadanie = ArrayList<Task>()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun MockTasks() {
        var pattern = "dd-MM-yyyy hh:mm"
        var simpleDateFormat = SimpleDateFormat(pattern, Locale("pl", "PL"))
        _tasks = ArrayList()
        repeat((6..10).random()) {
            indexTask ->
            val newTask = Task(
                 listOfTasks[indexTask],
                 simpleDateFormat.format(Date())
            )
            _tasks?.add(newTask)
            zadanie.add(newTask)
        }
    }
}