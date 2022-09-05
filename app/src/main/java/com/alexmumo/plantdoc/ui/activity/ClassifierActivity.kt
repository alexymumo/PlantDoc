package com.alexmumo.plantdoc.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.alexmumo.plantdoc.databinding.ActivityClassifierBinding
import com.alexmumo.plantdoc.ml.Classifier
import java.io.IOException

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

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        binding = ActivityClassifierBinding.inflate(layoutInflater)
        setContentView(binding.root)
        classifier = Classifier(assets, modelPath, labelPath, inputSize)

        resources.assets.open(samplePath).use {
            bitmap = BitmapFactory.decodeStream(it)
            bitmap = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)
            binding.imageView.setImageBitmap(bitmap)
        }
        binding.camera.setOnClickListener {
            val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(camera, cameraRequestCode)
        }
        binding.gallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, galleryRequestCode)
        }
        binding.predict.setOnClickListener {
            val predict = classifier.recognizeImage(bitmap).firstOrNull()
            binding.tvResults.text = predict?.title + "\n Confidence:" + predict?.confidence
        }
    }

    @SuppressLint("SetTextI18n")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequestCode) {
            if (requestCode == Activity.RESULT_OK && data != null) {
                bitmap = data.extras!!.get("data") as Bitmap
                bitmap = scaleImage(bitmap)
                val toast = Toast.makeText(this, ("Image crop to: w= ${bitmap.width} h= ${bitmap.height}"), Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 20)
                toast.show()
                binding.imageView.setImageBitmap(bitmap)
                // val output = classifier.recognizeImage(scaleImage(bitmap)).firstOrNull()
                // binding.tvResults.text = output?.title
                // binding.tvResults.text = output?.confidence.toString()
            }
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

    private fun scaleImage(bitmap: Bitmap?): Bitmap {
        val originalWidth = bitmap!!.width
        val originHeight = bitmap.height
        val scaleWidth = inputSize.toFloat() / originalWidth
        val scaleHeight = inputSize.toFloat() / originHeight
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originHeight, matrix, true)
    }
}
