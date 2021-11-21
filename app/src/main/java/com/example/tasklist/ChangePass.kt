package com.example.tasklist

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tasklist.databinding.FragmentChangePassBinding
import android.content.SharedPreferences
import android.net.wifi.WifiEnterpriseConfig
import android.widget.Toast
import java.lang.Exception
import java.math.BigInteger
import java.security.MessageDigest


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChangePass.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChangePass : Fragment(){
    private lateinit var binding: FragmentChangePassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreference =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val savedString = sharedPreference.getString("STRING_KEY", null)
        Toast.makeText(requireActivity(),savedString,Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_change_pass, container, false
        )


        binding.change = this@ChangePass
        return binding.root
    }

    fun goToList() {

        val sharedPreference =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val sharedPass = sharedPreference.getString("STRING_KEY", null)
        val oldPass = toMD5Hash(binding.passwordOld.text.toString())
        var newPass = binding.passwordNew.text.toString()

        if(oldPass.equals(sharedPass,false)) {
            if (newPass.length >= 4){
                newPass = toMD5Hash(newPass)
                savePass(newPass)
                Toast.makeText(requireActivity(),"Password updated!",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_changePass_to_taskListFragment)
            }
            else{
                Toast.makeText(requireActivity(),"Password too short,it should have at least 4 characters!",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_changePass_self)
            }

        }
        else{
            Toast.makeText(requireActivity(),"Password is incorrect, try again!",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_changePass_self)
        }
    }


    private fun savePass(pass: String){
        val sharedPreference =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.apply{
            putString("STRING_KEY",pass)
        }.apply()
    }


    private fun toMD5Hash(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }


    private fun loadData(){
        val sharedPreference =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val savedString = sharedPreference.getString("STRING_KEY", null)

    }

}