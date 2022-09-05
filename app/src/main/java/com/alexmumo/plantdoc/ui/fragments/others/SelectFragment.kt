package com.alexmumo.plantdoc.ui.fragments.others

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.alexmumo.plantdoc.databinding.FragmentSelectBinding

class SelectFragment : DialogFragment() {
    private lateinit var binding: FragmentSelectBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSelectBinding.inflate(inflater, container, false)
        return binding.root
    }
}
