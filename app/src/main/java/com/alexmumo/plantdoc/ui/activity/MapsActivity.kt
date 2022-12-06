package com.alexmumo.plantdoc.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import com.alexmumo.plantdoc.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setUpLocationClient()
    }

    private lateinit var fusedLocClient: FusedLocationProviderClient

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        getCurrentLocation()
        //val dekut = LatLng(-34.0, 150.1)
        //googleMap.addMarker(MarkerOptions().position(dekut).title("Northern Bright"))
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(dekut))
    }
    private fun setUpLocationClient() {
        fusedLocClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun requestLocPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION)
    }
    companion object {
        private const val REQUEST_LOCATION = 1
        private const val TAG = "MapActivity"


    }
    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocPermissions()
        } else {
            fusedLocClient.lastLocation.addOnCompleteListener {
                val location = it.result
                val database: FirebaseDatabase = FirebaseDatabase.getInstance()
                val ref : DatabaseReference = database.getReference("farmerlocation")
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    googleMap.addMarker(MarkerOptions().position(latLng).title("farmer"))
                    val update = CameraUpdateFactory.newLatLngZoom(latLng,16.0f)
                    googleMap.moveCamera(update)
                    ref.setValue(location)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                Log.e(TAG, "Location Permission Denied")
            }
        }
    }
}
