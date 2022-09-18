package com.alexmumo.plantdoc.ui.fragments.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alexmumo.plantdoc.R
import com.alexmumo.plantdoc.databinding.FragmentHomeBinding
import com.alexmumo.plantdoc.util.EventObserver
import com.alexmumo.plantdoc.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var circularImage: CircleImageView
    private lateinit var username: TextView
    private lateinit var notificationImage: ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        username = view.findViewById(R.id.tvUsername)
        circularImage = view.findViewById(R.id.profileImage)
        notificationImage = view.findViewById(R.id.notificationImage)

        circularImage.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
        notificationImage.setOnClickListener {
            findNavController().navigate(R.id.notificationFragment)
        }
        subscribeToObservers()
        subscribeToListeners()
        // subscribeToProfileObservers()
        return view
    }

    private fun subscribeToListeners() {
        binding.cardViewScan.setOnClickListener {
            findNavController().navigate(R.id.classifierActivity)
        }
    }

    /* private fun subscribeToProfileObservers() {
         userViewModel.user.observe(
             viewLifecycleOwner,
             EventObserver(
                 onError = {},
                 onLoading = {},

             ) { user ->
                 Glide.with(circularImage).load(user.farmerUrl).centerCrop().into(circularImage)
             }
         )
     }

     */

    @SuppressLint("SetTextI18n")
    private fun subscribeToObservers() {
        userViewModel.user.observe(
            viewLifecycleOwner,
            EventObserver(
                onLoading = {
                },
                onError = {
                }
            ) { user ->
                username.text = "Hello ${user.username?.substring(0,user.username.indexOf(' '))},"
            }
        )
    }
}
