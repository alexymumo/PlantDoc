package com.alexmumo.plantdoc.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alexmumo.plantdoc.R
import com.alexmumo.plantdoc.databinding.FragmentLoginBinding
import com.alexmumo.plantdoc.viewmodels.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        initForgot()
        initRegister()
        binding.loginBtn.setOnClickListener {
            loginUser()
        }
        return binding.root
    }

    private fun loginUser() {
        val email = binding.emailLayout.editText?.text.toString()
        val password = binding.passwordLayout.editText?.text.toString()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(requireContext(), "Logged In", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.homeFragment)
            } else {
                Toast.makeText(requireContext(), "Failed", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initRegister() {
        binding.registerTv.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
    }

    private fun initForgot() {
        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.forgotPassword)
        }
    }

    /*
    private fun initLogin() {
        viewModel.login.observe(
            viewLifecycleOwner,
            EventObserver(
                onError = {
                },
                onLoading = {
                }
            ) {
                findNavController().navigate(R.id.homeFragment)
            }
        )
    }*/
}
