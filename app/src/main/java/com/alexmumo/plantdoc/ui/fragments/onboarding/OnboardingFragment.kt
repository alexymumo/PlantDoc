package com.alexmumo.plantdoc.ui.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexmumo.plantdoc.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOnboardingBinding.inflate(layoutInflater)
        return binding.root
    }
}
