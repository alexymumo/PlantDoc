package com.alexmumo.plantdoc.ui.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexmumo.plantdoc.databinding.FragmentFarmersListBinding

class FarmersList : Fragment() {
    private lateinit var binding: FragmentFarmersListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFarmersListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
