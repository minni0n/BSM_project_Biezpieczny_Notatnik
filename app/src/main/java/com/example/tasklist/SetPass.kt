package com.example.tasklist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.tasklist.databinding.FragmentSetPassBinding
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.lang.Exception
import java.math.BigInteger
import java.security.MessageDigest

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SetPass.newInstance] factory method to
 * create an instance of this fragment.
 */
class SetPass : Fragment() {
    private lateinit var binding: FragmentSetPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_set_pass, container, false
        )

        binding.setpass = this@SetPass
        return binding.root
    }

    fun goToList() {
        val pass = binding.password.text.toString()

        if (pass.length >= 4) {
            savePass(pass)
            Toast.makeText(requireActivity(), "Password is set", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_setPass_to_taskListFragment)
        } else {
            Toast.makeText(requireActivity(),"Password too short,it should have at least 4 characters!",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_setPass_self)
        }
    }


    private fun savePass(pass: String) {

        val hash = HashString()
        val password = hash.hashString(pass)

        val sharedPreference =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.apply {
            putString("STRING_KEY", password)
        }.apply()
    }

}
