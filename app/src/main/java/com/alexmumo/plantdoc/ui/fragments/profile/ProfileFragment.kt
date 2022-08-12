package com.alexmumo.plantdoc.ui.fragments.profile

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alexmumo.plantdoc.R
import com.alexmumo.plantdoc.databinding.FragmentProfileBinding
import com.alexmumo.plantdoc.viewmodels.UserViewModel

class ProfileFragment : Fragment() {
    private var currentUri: Uri? = null
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding
    private val pickerContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        currentUri = uri
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        logOutUser()
        aboutUs()
        selectImage()
        return binding.root
    }

    private fun selectImage() {
        binding.profileImage.setOnClickListener {
            pickerContent.launch("image/*")
        }
    }

    private fun aboutUs() {
        binding.tvAboutUs.setOnClickListener {
            findNavController().navigate(R.id.aboutFragment)
        }
    }

    private fun logOutUser() {
        binding.tvlogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage("Are you sure you want to exit")
                .setCancelable(false)
                .setPositiveButton(
                    "Yes",
                    DialogInterface.OnClickListener { _, _ ->
                        findNavController().navigate(R.id.dashboardFragment)
                    }
                )
                .setNegativeButton("No", null)
                .show()
        }
    }
}
