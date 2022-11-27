package com.alexmumo.plantdoc.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.alexmumo.plantdoc.data.entity.Plant
import com.alexmumo.plantdoc.databinding.ActivityClassifierBinding
import com.alexmumo.plantdoc.ml.Classifier
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.IOException

@Suppress("DEPRECATION")
class ClassifierActivity : AppCompatActivity() {
    private lateinit var bitmap: Bitmap
    private lateinit var classifier: Classifier
    private lateinit var binding: ActivityClassifierBinding

    private val cameraRequestCode = 23
    private val galleryRequestCode = 0

    private val inputSize = 224
    private val modelPath = "maize.tflite"
    private val labelPath = "labels.txt"
    private val samplePath = "sample.jpg"
    private lateinit var databaseReference: DatabaseReference

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassifierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("Disease")
        classifier = Classifier(assets, modelPath, labelPath, inputSize)

        resources.assets.open(samplePath).use {
            bitmap = BitmapFactory.decodeStream(it)
            bitmap = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)
            binding.imageView.setImageBitmap(bitmap)
        }
        binding.camera.setOnClickListener {
            if (hasPermission()) {
                openCamera()
            } else {
                requestPermission()
            }
        }
        binding.gallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, galleryRequestCode)
        }
        binding.predict.setOnClickListener {
            val predict = classifier.recognizeImage(bitmap).firstOrNull()
            binding.tvResults.text = predict?.title + "\n Confidence:" + predict?.confidence

            // saving results to realtime database
            val diseaseId = databaseReference.push().key
            val disease = predict?.title.toString()
            val confidence = predict?.confidence.toString()
            val results = Plant(diseaseId, disease, confidence)
            if (diseaseId != null) {
                databaseReference.child("Results").child(diseaseId).setValue(results)
            }
        }
    }

    private fun requestPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            Toast.makeText(this, "Camera Permission Required", Toast.LENGTH_LONG).show()
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), cameraRequestCode)
        }
    }

    private fun hasPermission(): Boolean {
        return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun openCamera() {
        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(camera, cameraRequestCode)
    }

    @SuppressLint("SetTextI18n")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequestCode) {
            val photo = data?.extras!!.get("data") as Bitmap
            binding.imageView.setImageBitmap(photo)
            classifier.recognizeImage(scaleImage(photo)).firstOrNull()
            // binding.tvResults.text = output?.title
            // binding.tvResults.text = output?.confidence.toString()

            /*if (requestCode == Activity.RESULT_OK && data != null) {
                val photo = data.extras!!.get("data") as Bitmap
                bitmap = scaleImage(photo)
                // val toast = Toast.makeText(this, ("Image crop to: w= ${bitmap.width} h= ${bitmap.height}"), Toast.LENGTH_LONG)
                // toast.setGravity(Gravity.BOTTOM, 0, 20)
                // toast.show()
                //binding.imageView.setImageBitmap(photo)
                //val output = classifier.recognizeImage(scaleImage(photo)).firstOrNull()
               // binding.tvResults.text = output?.title
                //binding.tvResults.text = output?.confidence.toString()
            }*/
        } else if (requestCode == galleryRequestCode) {
            if (data != null) {
                val uri = data.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                bitmap = scaleImage(bitmap)
                binding.imageView.setImageBitmap(bitmap)
            }
        } else {
            Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == cameraRequestCode) {
            if (hasAllPermissions(grantResults)) {
                openCamera()
            } else {
                requestPermission()
            }
        }
    }

    private fun hasAllPermissions(grantResults: IntArray): Boolean {
        for (results in grantResults) {
            if (results == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    private fun scaleImage(bitmap: Bitmap): Bitmap {
        val originalWidth = bitmap.width
        val originHeight = bitmap.height
        val scaleWidth = inputSize.toFloat() / originalWidth
        val scaleHeight = inputSize.toFloat() / originHeight
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originHeight, matrix, true)
    }
}
