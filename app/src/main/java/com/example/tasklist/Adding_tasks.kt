package com.example.tasklist

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tasklist.data.Database
import com.example.tasklist.data.Task
import com.example.tasklist.databinding.FragmentAddingTasksBinding
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Adding_tasks.newInstance] factory method to
 * create an instance of this fragment.
 */
class Adding_tasks : Fragment() {


    private lateinit var binding: FragmentAddingTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_adding_tasks, container, false
        )

        binding.fragment = this@Adding_tasks
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun goToTasks() {
        val pattern = "dd-MM-yyyy hh:mm"
        val simpleDateFormat = SimpleDateFormat(pattern, Locale("pl", "PL"))
        val textTask = binding.editTextName.text.toString()
        val dateTask = simpleDateFormat.format(Date())

        if (textTask != "") {
            val secretKey = getSecretKey()
            val crypting = Cryption()
            val iv = IvParameterSpec(ByteArray(16))
            val taskCrypred = crypting.encrypt(textTask, secretKey, iv)
            val taskToAdd = Task(taskCrypred, dateTask)

            Database.taskDao.addTask(taskToAdd)
        }
        findNavController().navigate(R.id.action_adding_tasks_to_taskListFragment)
    }

    private fun getPass(): String? {
        val sharedPreference =
            requireActivity().getSharedPreferences("KEY", Context.MODE_PRIVATE)
        val pass = sharedPreference.getString("KEY_SECRET", null)
        return pass
    }

    private fun getSecretKey(): SecretKeySpec {
        return SecretKeySpec(getPass()?.removeRange(0, 32)?.toByteArray(), "AES")
    }

}
