package com.example.tasklist

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock.sleep
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tasklist.databinding.FragmentLoginPageBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import java.security.SecureRandom
import java.util.*
import javax.crypto.spec.SecretKeySpec

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
    private var counter: Int = 1


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login_page, container, false
        )

        checkIfPassSet()


        binding.login = this@LoginPage
        return binding.root
    }

    fun goToList() {

        val hash = HashString()
        val insertedText = hash.hashString(binding.password.text.toString())


        if(insertedText.equals(getPass(),false))
        {
            findNavController().navigate(R.id.action_loginPage_to_taskListFragment)
        }
        else{
            if (counter%5 == 0 && counter != 0){
                counter = 1
                Toast.makeText(requireActivity(),"You have been timed out for 30 second!",Toast.LENGTH_LONG).show()
                //toSleep()
            }
            else{
                counter++
                Toast.makeText(requireActivity(),"Your password is incorrect. Try again!",Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun toSleep(){
        sleep(5000)
    }

    fun getPass(): String? {
        val sharedPreference =
            requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val pass = sharedPreference.getString("STRING_KEY", null)
        return pass
    }

    private fun checkIfPassSet(){

        if(getPass() == null){
            findNavController().navigate(R.id.action_loginPage_to_setPass)
        }
    }

}