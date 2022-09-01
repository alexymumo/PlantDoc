package com.alexmumo.plantdoc.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alexmumo.plantdoc.R
import com.alexmumo.plantdoc.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
    }
}
