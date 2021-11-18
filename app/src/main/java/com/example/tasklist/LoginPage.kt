package com.example.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tasklist.databinding.FragmentLoginPageBinding
import android.content.SharedPreferences
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [activity_login_page.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginPage : Fragment(){
    private lateinit var binding: FragmentLoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login_page, container, false
        )


        binding.login = this@LoginPage
        return binding.root
    }

    fun goToList() {
        if(binding.password.text.toString().equals("pass",true)){
            findNavController().navigate(R.id.action_loginPage_to_taskListFragment)
        }
        else{
            findNavController().navigate(R.id.action_loginPage_self)
        }
    }


}