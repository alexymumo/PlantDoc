package com.alexmumo.plantdoc.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexmumo.plantdoc.R

class AnotherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_another)
    }
}

/*

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alexmumo.plantdoc.databinding.ActivityClassifierBinding
import com.alexmumo.plantdoc.ml.Classifier
import com.alexmumo.plantdoc.ml.Maize
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
/*
class ClassifierActivity : AppCompatActivity() {
    private lateinit var bitmap: Bitmap
    private lateinit var classifier: Classifier
    private lateinit var binding: ActivityClassifierBinding

    private val cameraRequestCode = 0
    private val galleryRequestCode = 2

    private val GALLERY_CODE = 1

    private val inputSize = 224
    private val modelPath = "maize.tflite"
    private val labelPath = "labels.txt"
    private val samplePath = "sample.jpg"

    // request permission
    private val takePicturePreview = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            binding.imageView.setImageBitmap(bitmap)
            (bitmap)
        }
    }

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            takePicturePreview.launch(null)
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
        }
    }

    private val onresult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        onResultReceived(GALLERY_CODE, result)
    }

    private fun onResultReceived(requestCode: Int, result: ActivityResult?) {
        when (requestCode) {
            GALLERY_CODE -> {
                if (result?.resultCode == Activity.RESULT_OK) {
                    result.data?.data?.let { uri ->
                        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                        binding.imageView.setImageBitmap(bitmap)
                        (bitmap)
                    }
                } else {
                    Log.d("TAG", "Error in selecting image")
                }
            }
        }
    }

    private fun outputGenerator(bitmap: Bitmap) {
        val model = Maize.newInstance(this)

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)

        val tensorImage = TensorImage(DataType.FLOAT32)

        tensorImage.load(inputFeature0)

        val byteBuffer = tensorImage.buffer

        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)

        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray

        // Releases model resources if no longer used.
        model.close()
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        binding = ActivityClassifierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val takePicturePreview = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                binding.imageView.setImageBitmap(bitmap)
            }
        }

        val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                takePicturePreview.launch(null)
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
            }
        }

        val onresult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            onResultReceived(GALLERY_CODE, result)
        }

        fun onResultReceived(requestCode: Int, result: ActivityResult?) {
            when (requestCode) {
                GALLERY_CODE -> {
                    if (result?.resultCode == Activity.RESULT_OK) {
                        result.data?.data?.let { uri ->
                            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                            binding.imageView.setImageBitmap(bitmap)
                            (bitmap)
                        }
                    } else {
                        Log.d("TAG", "Error in selecting image")
                    }
                }
            }
        }

        classifier = Classifier(assets, modelPath, labelPath, inputSize)
        resources.assets.open(samplePath).use {
            bitmap = BitmapFactory.decodeStream(it)
            bitmap = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)
            binding.imageView.setImageBitmap(bitmap)
        }

        // capture image
        binding.camera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                takePicturePreview.launch(null)
            } else {
                requestPermission.launch(android.Manifest.permission.CAMERA)
            }
            // val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // startActivityForResult(camera, cameraRequestCode)
        }

        binding.gallery.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                onresult.launch(intent)
            } else {
                requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            // val intent = Intent(Intent.ACTION_PICK)
            // intent.type = "image/*"
            // startActivityForResult(intent, galleryRequestCode)
        }

        binding.predict.setOnClickListener {
            val predict = classifier.recognizeImage(bitmap).firstOrNull()
            binding.tvResults.text = predict?.title + "\n Confidence:" + predict?.confidence
        }
    }
}
/*
class Classifier(assetManager: AssetManager, modelPath: String, labelPath: String, inputSize: Int) {
    private var INTERPRETER: Interpreter
    private var LABEL_LIST: List<String>
    private val INPUT_SIZE: Int = inputSize
    private val PIXEL_SIZE: Int = 3
    private val IMAGE_MEAN = 0
    private val IMAGE_STD = 255.0f
    private val MAX_RESULTS = 3
    private val THRESHOLD = 0.4f

    data class Recognition(
        var id: String = "",
        var title: String = "",
        var confidence: Float = 0F
    ) {
        override fun toString(): String {
            return "Title = $title, Confidence = $confidence)"
        }
    }

    init {
        INTERPRETER = Interpreter(loadModelFile(assetManager, modelPath))
        LABEL_LIST = loadLabelList(assetManager, labelPath)
    }

    // load the labels
    private fun loadLabelList(assetManager: AssetManager, labelPath: String): List<String> {
        return assetManager.open(labelPath).bufferedReader().useLines { it.toList() }
    }

    // load the model
    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
    fun recognizeImage(bitmap: Bitmap): List<Recognition> {
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false)
        val byteBuffer = convertBitmapToByteBuffer(scaledBitmap)
        val result = Array(1) { FloatArray(LABEL_LIST.size) }
        INTERPRETER.run(byteBuffer, result)
        return getSortedResult(result)
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * INPUT_SIZE * INPUT_SIZE * PIXEL_SIZE)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(INPUT_SIZE * INPUT_SIZE)

        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        var pixel = 0
        for (i in 0 until INPUT_SIZE) {
            for (j in 0 until INPUT_SIZE) {
                val `val` = intValues[pixel++]

                byteBuffer.putFloat((((`val`.shr(16) and 0xFF) - IMAGE_MEAN) / IMAGE_STD))
                byteBuffer.putFloat((((`val`.shr(8) and 0xFF) - IMAGE_MEAN) / IMAGE_STD))
                byteBuffer.putFloat((((`val` and 0xFF) - IMAGE_MEAN) / IMAGE_STD))
            }
        }
        return byteBuffer
    }

    private fun getSortedResult(labelProbArray: Array<FloatArray>): List<Recognition> {
        Log.d("Classifier", "List Size:(%d, %d, %d)".format(labelProbArray.size, labelProbArray[0].size, LABEL_LIST.size))

        val pq = PriorityQueue(
            MAX_RESULTS,
            Comparator<Recognition> {
                    (_, _, confidence1), (_, _, confidence2)
                ->
                confidence1.compareTo(confidence2) * -1
            }
        )

        for (i in LABEL_LIST.indices) {
            val confidence = labelProbArray[0][i]
            if (confidence >= THRESHOLD) {
                pq.add(
                    Recognition(
                        "" + i,
                        if (LABEL_LIST.size > i) LABEL_LIST[i] else "Unknown", confidence
                    )
                )
            }
        }
        Log.d("Classifier", "pqsize:(%d)".format(pq.size))

        val recognitions = ArrayList<Recognition>()
        val recognitionsSize = Math.min(pq.size, MAX_RESULTS)
        for (i in 0 until recognitionsSize) {
            pq.poll()?.let { recognitions.add(it) }
        }
        return recognitions
    }
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



/*
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
             val output = classifier.recognizeImage(scaleImage(bitmap)).firstOrNull()
             binding.tvResults.text = output?.title
             binding.tvResults.text = output?.confidence.toString()
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

 */

 */