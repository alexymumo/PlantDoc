package com.alexmumo.plantdoc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexmumo.plantdoc.databinding.ActivityMachineBinding

class MachineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMachineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMachineBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_machine)
    }
}
