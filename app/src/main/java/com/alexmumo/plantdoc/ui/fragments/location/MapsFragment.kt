package com.alexmumo.plantdoc.ui.fragments.location

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.alexmumo.plantdoc.R
import com.alexmumo.plantdoc.data.entity.Location
import com.alexmumo.plantdoc.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.* // ktlint-disable no-wildcard-imports
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MapsFragment : Fragment(), OnMapReadyCallback {
    private val REQUEST_PERMISSION_CODE = 1
    private lateinit var binding: FragmentMapsBinding
    private var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference = firebaseDatabase.getReference("farmers_location")
    private lateinit var googleMap: GoogleMap
    private val callback = OnMapReadyCallback { googleMap ->
        // val current = LatLng(-0.3966685, 36.9586995)
        // val zoomLevel = 15f
        // googleMap.addMarker(MarkerOptions().position(current).title("Nyeri"))
        // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, zoomLevel))
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        getFarmersLocation()
        requestPermission()
    }

    private fun getFarmersLocation() {
        /*get farmers location at realtime*/
        val locationListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val location = snapshot.child("farmers_location").getValue(Location::class.java)
                    val flatitude = location?.Latitude
                    val flongitude = location?.Longitude
                    if (flatitude != null && flongitude != null) {
                        val latLog = LatLng(flatitude, flongitude)
                        googleMap.addMarker(MarkerOptions().position(latLog).title("Farmer"))
                        val update = CameraUpdateFactory.newLatLngZoom(latLog, 16.0f)
                        googleMap.moveCamera(update)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to fetch Farmers location", Toast.LENGTH_LONG).show()
            }
        }
        databaseReference = Firebase.database.reference
        databaseReference.addValueEventListener(locationListener)
    }

    /*
    * function to request permission
    * */
    private fun requestPermission() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            permissions.entries.forEach {
            }
        }
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    override fun onMapReady(googleMap: GoogleMap) {
        getFarmersLocation()
    }
}



