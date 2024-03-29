package com.alexmumo.plantdoc.ui.fragments.profile

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alexmumo.plantdoc.R
import com.alexmumo.plantdoc.databinding.FragmentProfileBinding
import com.alexmumo.plantdoc.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var currentUri: Uri? = null
    // @Inject lateinit var glide: RequestManager
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding
    private val pickerContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            currentUri = uri
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        initializerListeners()
        // subscribeToObservers()
        //subscribeToProfileObservers()
        return binding.root
    }
    /*

    private fun subscribeToProfileObservers() {
        userViewModel.curlImageUri.observe(
            viewLifecycleOwner,
            Observer {
                currentUri = it
                // glide.load(currentUri).into(binding.farmerProfile)
            }
        )
    }*/

    /*
    private fun subscribeToObservers() {
        userViewModel.user.observe(
            viewLifecycleOwner,
            EventObserver(
                onError = {
                },
                onLoading = {
                }
            ) { user ->
                binding.profileUsername.text = "${user.username}"
                binding.profileLocation.text = "${user.email}"
                // Glide.with(binding.farmerProfile).load(user.farmerUrl).into(binding.farmerProfile)
            }
        )
    }

     */

    private fun initializerListeners() {
        binding.logOutCardView.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton(
                    "Yes",
                    DialogInterface.OnClickListener { _, _ ->
                        FirebaseAuth.getInstance().signOut()
                        findNavController().navigate(R.id.dashboardFragment)
                    }
                )
                .setNegativeButton("No", null)
                .show()
        }
        binding.farmerProfile.setOnClickListener {
            pickerContent.launch("image/*")
        }
        /* binding.profileImage.setOnClickListener {
            pickerContent.launch("image/*")
        }
        binding.tvlogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage("Are you sure you want to exit")
                .setCancelable(false)
                .setPositiveButton(
                    "Yes",
                    DialogInterface.OnClickListener { _, _ ->
                        FirebaseAuth.getInstance().signOut()
                        findNavController().navigate(R.id.dashboardFragment)
                    }
                )
                .setNegativeButton("No", null)
                .show()
        }
        binding.tvAboutUs.setOnClickListener {
            findNavController().navigate(R.id.aboutFragment)
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}

        */
    }
}
