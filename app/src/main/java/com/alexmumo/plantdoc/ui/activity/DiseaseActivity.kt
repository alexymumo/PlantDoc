package com.alexmumo.plantdoc.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alexmumo.plantdoc.R
import com.alexmumo.plantdoc.data.entity.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DiseaseActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbReference: DatabaseReference = database.getReference("farmerlocation")
    private lateinit var diseaseBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disease)

        diseaseBtn = findViewById(R.id.btn_find_location)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        dbReference = Firebase.database.reference
        dbReference.addValueEventListener(locationListener)
    }
    val locationListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                val location = snapshot.child("farmerlocation").getValue(Location::class.java)
                val locationLat = location?.Latitude
                val locationLog = location?.Longitude

                diseaseBtn.setOnClickListener {
                    if (locationLat != null && locationLog != null) {
                        val latLng = LatLng(locationLat, locationLog)
                        map.addMarker(MarkerOptions().position(latLng).title("Plantdoc used here"))
                        val update = CameraUpdateFactory.newLatLngZoom(latLng, 16.0f)
                        map.moveCamera(update)
                    } else {
                        Log.e(TAG, "No Location")
                    }
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(applicationContext, "Could not read from database", Toast.LENGTH_LONG).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }
    companion object {
        private const val TAG = "DiseaseActivity"
    }
}
