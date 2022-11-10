package com.alexmumo.plantdoc.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alexmumo.plantdoc.R
import com.alexmumo.plantdoc.data.entity.User
import com.alexmumo.plantdoc.databinding.FragmentRegisterBinding
import com.alexmumo.plantdoc.viewmodels.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private val firebaseUserId: String = ""
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        firebaseAuth = FirebaseAuth.getInstance()
        binding.registerBtn.setOnClickListener {
            registerUser()
        }
        binding.signinTv.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        return binding.root
    }

    // register function
    private fun registerUser() {
        val email = binding.emailLayout.editText?.text.toString().trim()
        val name = binding.nameLayout.editText?.text.toString().trim()
        val phone = binding.locationLayout.editText?.text.toString().trim()
        val password = binding.passwordLayout.editText?.text.toString().trim()

        if (email.isEmpty() || name.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Cannot be empty", Toast.LENGTH_LONG).show()
            return
        }
        if (password.length < 6) {
            Toast.makeText(requireContext(), "Password cannot be less than 6", Toast.LENGTH_LONG).show()
            return
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userid = firebaseAuth.currentUser!!.uid
                val user = User(userid, email, name, phone, password)
                databaseReference.child(userid).setValue(user)
                Toast.makeText(requireContext(), "Registered", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.loginFragment)
                // startActivity(Intent(requireContext(), LoginFragment::class.java))
            } else {
                Toast.makeText(requireContext(), "Failed to register", Toast.LENGTH_LONG).show()
            }
        }
    }
}
