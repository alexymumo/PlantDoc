package com.alexmumo.plantdoc.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alexmumo.plantdoc.R
import com.alexmumo.plantdoc.databinding.FragmentLoginBinding
import com.alexmumo.plantdoc.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        initLogin()
        initForgot()
        initRegister()
        binding.loginBtn.setOnClickListener {
            viewModel.signInUser(
                binding.emailLayout.editText?.text.toString(),
                binding.passwordLayout.editText?.text.toString()
            )
        }
        return binding.root
    }

    private fun initRegister() {
    }

    private fun initForgot() {
        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.forgot_password)
        }
    }

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
    }
}
