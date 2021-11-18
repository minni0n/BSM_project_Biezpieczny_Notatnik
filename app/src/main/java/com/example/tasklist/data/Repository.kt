package com.example.tasklist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Repository {

    var tasks : MutableLiveData<ArrayList<Task>> = MutableLiveData()

    init {
        tasks.value = Database.taskDao.getAllTasks()

    }

    fun getAllTasks() : LiveData<ArrayList<Task>> {
        return tasks
    }
}