package com.example.tasklist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tasklist.databinding.FragmentLoginPageBinding
import android.content.SharedPreferences
import android.widget.Toast
import java.lang.Exception
import java.math.BigInteger
import java.security.MessageDigest
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

        checkIfPassSet()

        binding.login = this@LoginPage
        return binding.root
    }

    fun goToList() {

        val sharedPreference =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

        val insertedText = hashString(binding.password.text.toString())


        if(insertedText.equals(sharedPreference.getString("STRING_KEY", null),false)){
            findNavController().navigate(R.id.action_loginPage_to_taskListFragment)
        }
        else{
            Toast.makeText(requireActivity(),"Your password is incorrect. Try again!",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_loginPage_self)
        }
    }


    private fun checkIfPassSet(){
        val sharedPreference =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        if(sharedPreference.getString("STRING_KEY", null) == null){
            findNavController().navigate(R.id.action_loginPage_to_setPass)
        }
    }

    private fun hashString(input: String): String {
        return MessageDigest
            .getInstance("SHA-256")
            .digest(input.toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })
    }

}