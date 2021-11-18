package com.example.tasklist.data

import com.example.tasklist.data.dao.TaskDao

object Database {

    var _taskDao : TaskDao? = null
    val taskDao : TaskDao
    get() {
        if(_taskDao == null)
            _taskDao = TaskDao()
        return _taskDao!!
    }
}