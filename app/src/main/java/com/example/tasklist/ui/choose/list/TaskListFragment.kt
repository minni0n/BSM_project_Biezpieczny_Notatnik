package com.example.tasklist.ui.choose.list

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tasklist.*
import com.example.tasklist.data.DataSource
import com.example.tasklist.data.Database
import com.example.tasklist.data.Repository
import com.example.tasklist.data.Task
import com.example.tasklist.data.dao.TaskDao
import com.example.tasklist.databinding.FragmentTaskListBinding
import com.example.tasklist.ui.adapters.TaskAdapter
import com.example.tasklist.ui.adapters.TaskItemClickListener
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * A simple [Fragment] subclass.
 * Use the [TaskListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskListFragment : Fragment(), TaskItemClickListener {

    lateinit var mAdapter : TaskAdapter
    val sharedViewModel : TaskListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTaskListBinding>(
                inflater, R.layout.fragment_task_list, container, false
        )

        mAdapter = TaskAdapter(this)
        mAdapter.submitList(Repository.getAllTasks().value)
        binding.recyclerView.adapter = mAdapter
        binding.fragment = this@TaskListFragment
        subscribeUi(mAdapter, binding)

        return binding.root
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun subscribeUi(newAdapter: TaskAdapter, binding: FragmentTaskListBinding) {
        binding.recyclerView.adapter = newAdapter
        newAdapter.notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun chooseTask(task: Task): String {
        val iv = IvParameterSpec(ByteArray(16))
        val secretKey = SecretKeySpec(getPass()?.removeRange(0,32)?.toByteArray(), "AES")
        val crypting = Cryption()
        return crypting.decrypt(task.name, secretKey, iv)
       //Toast.makeText(activity, "Zosta??o wybrane zadanie $taskCrypred", Toast.LENGTH_LONG).show()
        // val taskToast = Toast.makeText(activity, "Zosta??o wybrane zadanie ${task.name}", Toast.LENGTH_LONG)
    }


    fun getPass(): String? {
        val sharedPreference =
            requireActivity().getSharedPreferences("KEY", Context.MODE_PRIVATE)
        val pass = sharedPreference.getString("KEY_SECRET", null)
        return pass
    }

    fun addTask(){
        findNavController().navigate(R.id.action_taskListFragment_to_adding_tasks)
    }

    fun clearSharedPref(){
        DataSource.zadanie.clear()
        Database.taskDao.saveInSharedPreferences()
        findNavController().navigate(R.id.action_taskListFragment_self)
    }

    fun goToPassChange(){
        findNavController().navigate(R.id.action_taskListFragment_to_changePass)
    }

}