package com.alexmumo.plantdoc.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alexmumo.plantdoc.databinding.FragmentForgotPasswordBinding
import com.alexmumo.plantdoc.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPassword : Fragment() {
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentForgotPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater, container, false)
        setUpObserver()
        return binding.root
    }

    /*
    * This method sets up the observer for the view model
    * */
    private fun setUpObserver() {
        binding.resetBtn.setOnClickListener {
            viewModel.forgotPassword(binding.emailLayout.editText?.text.toString())
        }
    }
}
