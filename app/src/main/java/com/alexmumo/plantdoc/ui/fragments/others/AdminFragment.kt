package com.alexmumo.plantdoc.ui.fragments.others

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexmumo.plantdoc.databinding.FragmentAdminBinding
import com.alexmumo.plantdoc.ui.adapters.FarmersAdapter
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminFragment : Fragment() {
    private lateinit var databaseReference: DatabaseReference

    private lateinit var adapter: FarmersAdapter
    private lateinit var binding: FragmentAdminBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        // databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        // databaseReference.addValueEventListener {

        // }
        adapter = FarmersAdapter()
    }
}
