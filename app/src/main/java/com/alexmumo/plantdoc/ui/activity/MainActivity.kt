package com.alexmumo.plantdoc.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alexmumo.plantdoc.R
import com.alexmumo.plantdoc.databinding.ActivityMainBinding
import com.alexmumo.plantdoc.ui.fragments.others.SelectFragment
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import kotlin.concurrent.thread

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // splash screen
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)

        NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.selectFragment -> {
                    val dialog = SelectFragment()
                    dialog.show(supportFragmentManager, "custom")
                    true
                }
                else -> false
            }
        }

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

fun main() {
    GlobalScope.launch {
        delay(1000)
        print("World")
    }
    print("Hello")
    Thread.sleep(1223)
}

fun old_main(args: Array<String>) {
    thread {
        sleep(1000)
        print("World")
    }
    print("Hello")
    Thread.sleep(1444)
}