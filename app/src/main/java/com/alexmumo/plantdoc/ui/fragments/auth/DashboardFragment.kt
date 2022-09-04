package com.alexmumo.plantdoc.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alexmumo.plantdoc.R
import com.alexmumo.plantdoc.databinding.FragmentDashboardBinding
import com.google.firebase.auth.FirebaseAuth

class DashboardFragment : Fragment() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var binding: FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // user is signed
            findNavController().navigate(R.id.homeFragment)
        } else {
            findNavController().navigate(R.id.loginFragment)
            // user is not signed
        }
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
