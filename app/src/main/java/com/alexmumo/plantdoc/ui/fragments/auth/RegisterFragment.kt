package com.alexmumo.plantdoc.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alexmumo.plantdoc.R
import com.alexmumo.plantdoc.databinding.FragmentRegisterBinding
import com.alexmumo.plantdoc.util.EventObserver
import com.alexmumo.plantdoc.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        initRegister()
        binding.registerBtn.setOnClickListener {
            viewModel.registerFarmer(
                /*binding.emailText.text.toString(),
                binding.nameText.text.toString(),
                binding.locationText.text.toString(),
                binding.passwordText.text.toString()*/
                binding.emailLayout.editText?.text.toString().trim(),
                binding.nameLayout.editText?.text.toString().trim(),
                binding.locationLayout.editText?.text.toString().trim(),
                binding.passwordLayout.editText?.text.toString().trim()
            )
        }
        binding.signinTv.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        return binding.root
    }

    private fun initRegister() {
        viewModel.register.observe(
            viewLifecycleOwner,
            EventObserver(
                onLoading = {
                    binding.progressBar.isVisible = true
                },
                onError = {
                    binding.progressBar.isVisible = false
                }
            ) {
                binding.progressBar.isVisible = false
                Toast.makeText(requireContext(), "Email Verification Send to your email", Toast.LENGTH_LONG).show()
            }
        )
    }
}
