package com.example.tasklist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tasklist.databinding.FragmentChangePassBinding


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
        val hash = HashString()
        val sharedPreference =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val sharedPass = sharedPreference.getString("STRING_KEY", null)
        val oldPass = hash.hashString(binding.passwordOld.text.toString())
        val newPass = binding.passwordNew.text.toString()
        val newPassHash = hash.hashString(newPass)

        if(oldPass.equals(sharedPass,false)) {
            when {
                newPassHash.equals(sharedPass,false) -> {
                    Toast.makeText( requireActivity(),
                        "Password cannot be similar to the previous password!",
                        Toast.LENGTH_SHORT).show()
                    //findNavController().navigate(R.id.action_changePass_self)
                }
                newPass.length >= 4 -> {
                    savePass(newPassHash)
                    Toast.makeText(requireActivity(),"Password updated!",Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_changePass_to_taskListFragment)
                }
                else -> {
                    Toast.makeText(requireActivity(),"Password too short,it should have at least 4 characters!",Toast.LENGTH_SHORT).show()
                    //findNavController().navigate(R.id.action_changePass_self)
                }
            }

        }
        else{
            Toast.makeText(requireActivity(),"Password is incorrect, try again!",Toast.LENGTH_SHORT).show()
            //findNavController().navigate(R.id.action_changePass_self)
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

}