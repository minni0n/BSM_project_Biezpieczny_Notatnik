package com.example.tasklist.ui.choose.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tasklist.R
import com.example.tasklist.data.DataSource
import com.example.tasklist.data.Database
import com.example.tasklist.data.Repository
import com.example.tasklist.data.Task
import com.example.tasklist.databinding.FragmentTaskListBinding
import com.example.tasklist.ui.adapters.TaskAdapter
import com.example.tasklist.ui.adapters.TaskItemClickListener

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
    private fun subscribeUi(newAdapter: TaskAdapter, binding: FragmentTaskListBinding) {
        binding.recyclerView.adapter = newAdapter
        newAdapter.notifyDataSetChanged()
    }

    override fun chooseTask(task: Task) {
        val taskToast = Toast.makeText(activity, "Zostało wybrane zadanie ${task.name}", Toast.LENGTH_LONG)
        taskToast.show()
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