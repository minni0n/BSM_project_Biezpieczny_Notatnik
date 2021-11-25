package com.example.tasklist.data.dao

import android.content.Context
import android.content.SharedPreferences
import com.example.tasklist.MainApplication
import com.example.tasklist.data.DataSource
import com.example.tasklist.data.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TaskDao {


    fun getAllTasks() : ArrayList<Task> {
        return DataSource.zadanie
    }

    companion object {
        public const val SHARED_PREFERENCES_TAG = "TaskDao"
    }
    init {
        readFromSharedPreferences()
    }

    fun saveInSharedPreferences() {
        val context = MainApplication.applicationContext()
        var dataToSave = getAllTasks()

        saveListInSharedPreferences(context, SHARED_PREFERENCES_TAG, dataToSave)
    }

    /** save givent list of learningProcessData into given tag of the shared preferences*/
    private fun saveListInSharedPreferences(
        context: Context,
        sharedPreferencesTag: String,
        dataToSave: ArrayList<Task>
    ) {
        var sharedPreferences = context.getSharedPreferences(sharedPreferencesTag, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(dataToSave)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(sharedPreferencesTag, json)
        editor.commit()
    }

    fun readFromSharedPreferences() {
        val context = MainApplication.applicationContext()
        val readedData: List<Task> = loadListFromSharedPreferneces(
            context, SHARED_PREFERENCES_TAG
        )
        DataSource.zadanie.clear()
        DataSource.zadanie.addAll(readedData)
    }

     fun loadListFromSharedPreferneces(
        context: Context,
        sharedPreferencesTag: String
    ): List<Task> {
        val gson = Gson()
        var sharedPreferences = context.getSharedPreferences(sharedPreferencesTag, Context.MODE_PRIVATE)

        val json = sharedPreferences.getString(SHARED_PREFERENCES_TAG, "")
        if (json == null || json.length < 10)
            return arrayListOf()
        val itemType = object : TypeToken<List<Task>>() {}.type
        val dataListFromSharedPreferneces: List<Task> =
            gson.fromJson<List<Task>>(json, itemType)
        return dataListFromSharedPreferneces
    }


    fun addTask(task: Task) {
        DataSource.zadanie.add(task)
        saveInSharedPreferences()
    }
}