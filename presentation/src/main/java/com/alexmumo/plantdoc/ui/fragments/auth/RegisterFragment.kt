package com.alexmumo.plantdoc.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alexmumo.plantdoc.databinding.FragmentRegisterBinding
import com.alexmumo.plantdoc.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        initRegister()
        binding.registerBtn.setOnClickListener {
            viewModel.registerUser(
                binding.nameLayout.editText?.text.toString(),
                binding.emailLayout.editText?.text.toString(),
                binding.locationLayout.editText?.text.toString(),
                binding.passwordLayout.editText?.text.toString()
            )
        }
        return binding.root
    }

    private fun initRegister() {
        viewModel.register.observe(
            viewLifecycleOwner,
            EventObserver(
                onLoading = {
                },
                onError = {
                }
            ) {
                Toast.makeText(requireContext(), "Successful", Toast.LENGTH_LONG).show()
            }
        )
    }
}
