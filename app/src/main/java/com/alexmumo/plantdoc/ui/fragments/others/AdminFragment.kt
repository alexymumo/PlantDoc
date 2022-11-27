package com.alexmumo.plantdoc.ui.fragments.others

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexmumo.plantdoc.data.entity.User
import com.alexmumo.plantdoc.databinding.FragmentAdminBinding
import com.alexmumo.plantdoc.ui.adapters.FarmersAdapter
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminFragment : Fragment() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    var users: ArrayList<User>? = null
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
        firebaseDatabase = FirebaseDatabase.getInstance()
        binding.rvFarmerList.setHasFixedSize(true)
        binding.rvFarmerList.layoutManager = LinearLayoutManager(requireContext())

        users = arrayListOf<User>()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()) {
                    for (user in snapshot.children) {
                        val s = user.getValue(User::class.java)
                        users?.add(s!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
