package com.alexmumo.plantdoc.ui.fragments.profile

import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alexmumo.plantdoc.databinding.FragmentProfileBinding
import com.alexmumo.plantdoc.util.EventObserver
import com.alexmumo.plantdoc.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var currentUri: Uri? = null
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
        subscribeToObservers()
        return binding.root
    }

    private fun subscribeToObservers() {
        userViewModel.user.observe(
            viewLifecycleOwner,
            EventObserver(
                onError = {
                },
                onLoading = {
                }
            ) { user ->
                binding.profileUsername.text = "${user.name}"
                binding.profileLocation.text = "${user.email}"
                // binding.profileEmail.text = "${user.email}"
                // binding.profileUsername.text = "${user.name}"
            }
        )
    }

    private fun initializerListeners() {

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
