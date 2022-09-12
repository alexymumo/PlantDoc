package com.alexmumo.plantdoc.ui.fragments.others

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alexmumo.plantdoc.databinding.FragmentSelectBinding
import com.alexmumo.plantdoc.ml.Classifier
import com.alexmumo.plantdoc.ml.Maize
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder


@Suppress("DEPRECATION")
@AndroidEntryPoint
class SelectFragment : Fragment() {

    private lateinit var bitmap: Bitmap
    private lateinit var classifier: Classifier
    val imageSize = 224

    private val cameraRequestCode = 23
    private val galleryRequestCode = 0

    private val inputSize = 224
    private val modelPath = "maize.tflite"
    private val labelPath = "labels.txt"
    private val samplePath = "sample.jpg"

    private lateinit var binding: FragmentSelectBinding
    val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        startCamera()
    }

    private fun startCamera() {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSelectBinding.inflate(inflater, container, false)
        setUpListeners()
        return binding.root
    }

    private fun setUpListeners() {
        binding.btnCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(camera, cameraRequestCode)
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), cameraRequestCode)
            }
        }
        binding.btnGallery.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK)
            gallery.type = "image/*"
            startActivityForResult(gallery, galleryRequestCode)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequestCode) {
            if (requestCode == Activity.RESULT_OK && data != null) {
                bitmap = data.extras!!.get("data") as Bitmap
                val dimension = Math.min(bitmap.width, bitmap.height)
                bitmap = ThumbnailUtils.extractThumbnail(bitmap, dimension, dimension)
                binding.previewImage.setImageBitmap(bitmap)
                bitmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, false)
                outputGenerator(bitmap)
            }
        } else if (requestCode == galleryRequestCode) {
            if (data != null) {
                val uri = data.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                binding.previewImage.setImageBitmap(bitmap)
                bitmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, false)
                outputGenerator(bitmap)
                // binding.imageView.setImageBitmap(bitmap)
            }
        } else {
            println("Error occurred")
            // Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun outputGenerator(bitmap: Bitmap) {
        try {
            val model = Maize.newInstance(requireContext())

            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)

            val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3)

            byteBuffer.order(ByteOrder.nativeOrder())

            val intValues = IntArray(224 * 224)

            bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

            var pixel = 0

            for (i in 0 until 224) {
                for (j in 0 until 224) {
                    val `val` = intValues[pixel++]
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
                }
            }
            inputFeature0.loadBuffer(byteBuffer)

            // Run model inference and get result
            val outputs = model.process(inputFeature0)

            val outputFeature0: TensorBuffer = outputs.outputFeature0AsTensorBuffer

            val confidences = outputFeature0.floatArray

            // find the index of the class with biggest confidence
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }

            val classes = arrayOf("Gray Leaf Spot", "Healthy", "Maize Common Smut", "Maize Streak Virus", "Northern Leaf Blight")
            // classes[maxPos]
            binding.tvResults.text = classes[maxPos]
            // Releases model resources if no longer used.
            model.close()
        } catch (e: IOException) {
        }
    }
}
