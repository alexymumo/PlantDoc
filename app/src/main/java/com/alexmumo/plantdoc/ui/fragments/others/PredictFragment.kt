package com.alexmumo.plantdoc.ui.fragments.others

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexmumo.plantdoc.databinding.FragmentPredictBinding

class PredictFragment : Fragment() {
    private lateinit var binding: FragmentPredictBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPredictBinding.inflate(inflater, container, false)
        return binding.root
    }
}
