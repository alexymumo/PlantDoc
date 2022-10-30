package com.alexmumo.plantdoc.ui.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alexmumo.plantdoc.databinding.FragmentFarmersListBinding
import com.alexmumo.plantdoc.ui.adapters.FarmersAdapter
import com.alexmumo.plantdoc.util.EventObserver
import com.alexmumo.plantdoc.viewmodels.FarmersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FarmersList : Fragment() {
    private val farmersViewModel: FarmersViewModel by viewModels()
    private lateinit var adapter: FarmersAdapter

    // private val adapter: FarmersAdapter by lazy { FarmersAdapter() }
    private lateinit var binding: FragmentFarmersListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFarmersListBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        subscribeToObservers()
        return view
    }

    private fun subscribeToObservers() {
        farmersViewModel.farmer.observe(
            viewLifecycleOwner,
            EventObserver(
                onError = {
                },
                onLoading = {
                }
            ) { farmers ->
                adapter.submitList(farmers)
                binding.rvFarmerList.adapter = adapter
            }
        )
    }
}
