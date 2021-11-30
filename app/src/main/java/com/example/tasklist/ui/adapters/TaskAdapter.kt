package com.example.tasklist.ui.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.Adding_tasks
import com.example.tasklist.Cryption
import com.example.tasklist.LoginPage
import com.example.tasklist.R
import com.example.tasklist.data.Task
import com.example.tasklist.databinding.ItemTaskBinding
import com.example.tasklist.ui.choose.list.TaskListFragment
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class TaskAdapter internal constructor(
    private val nListener : TaskItemClickListener
): ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task, nListener)
    }


    class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(currentTask: Task, listener: TaskItemClickListener) {
            binding.task = currentTask
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : TaskViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: ItemTaskBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.item_task,
                    parent, false
                )
                return TaskViewHolder(binding)
            }
        }

    }
}

interface TaskItemClickListener {
    @RequiresApi(Build.VERSION_CODES.O)
    fun chooseTask(task: Task): String {
        val iv = IvParameterSpec(ByteArray(16))
        val secretKey = SecretKeySpec(TaskListFragment().getPass()?.removeRange(0,32)?.toByteArray(), "AES")
        val crypting = Cryption()

        return crypting.decrypt(task.name, secretKey, iv)
    }
}

private class TaskDiffCallback : DiffUtil.ItemCallback<Task> () {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.name.contentEquals(newItem.name)
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }

}