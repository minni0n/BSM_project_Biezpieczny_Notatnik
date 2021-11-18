package com.example.tasklist.ui.choose.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tasklist.data.Repository
import com.example.tasklist.data.Task

class TaskListViewModel : ViewModel() {
    var tasks: LiveData<ArrayList<Task>> = Repository.getAllTasks()

}
