package com.alexmumo.plantdoc.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alexmumo.plantdoc.R
import com.alexmumo.plantdoc.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView

@AndroidEntryPoint
class HomeFragment : Fragment() {
    // private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var circularImage: CircleImageView
    private lateinit var username: TextView

    // private lateinit var locationCard: CardView
    // private lateinit var adminCard: CardView
    private lateinit var notificationImage: ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        username = view.findViewById(R.id.tvUsername)
        circularImage = view.findViewById(R.id.profileImage)
        notificationImage = view.findViewById(R.id.notificationImage)
        binding.cardViewLocation.setOnClickListener {
            findNavController().navigate(R.id.mapsFragment)
        }
        /*circularImage.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
        notificationImage.setOnClickListener {
            findNavController().navigate(R.id.notificationFragment)
        }
        binding.cardViewScan.setOnClickListener {
            findNavController().navigate(R.id.classifierActivity)
        }
        binding.cardViewAdmin.setOnClickListener {
            findNavController().navigate(R.id.adminFragment)
        }*/
        binding.cardViewAdmin.setOnClickListener {
            findNavController().navigate(R.id.adminFragment)
        }
        binding.cardViewScan.setOnClickListener {
            findNavController().navigate(R.id.classifierActivity)
        }
        return view
    }
}
