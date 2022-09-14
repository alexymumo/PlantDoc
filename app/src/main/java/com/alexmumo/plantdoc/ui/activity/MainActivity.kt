package com.alexmumo.plantdoc.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alexmumo.plantdoc.R
import com.alexmumo.plantdoc.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // splash screen
        //installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment || destination.id == R.id.registerFragment || destination.id == R.id.dashboardFragment) {
                binding.bottomNav.visibility = View.GONE
            } else if (destination.id == R.id.selectFragment) {
                binding.bottomNav.visibility = View.GONE
                // val dialog = SelectFragment()
                // dialog.show(supportFragmentManager, "custom")
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }
}
