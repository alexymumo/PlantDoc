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
/*
*        val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Plant Doc")
                .setCancelable(false)
                .setPositiveButton(
                    "Gallery",
                    DialogInterface.OnClickListener { dialog, id ->
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        startActivityForResult(intent, galleryRequestCode)
                    }
                ).setNegativeButton(
                    "Cancel",
                    DialogInterface.OnClickListener {
                            dialog, id ->
                        dialog.cancel()
                    }
                )
            val alert = dialogBuilder.create()
            alert.show()
                // binding.bottomNav.background = null
        // binding.bottomNav.menu.getItem(2).isEnabled = false
 */
* */
// return super.onOptionsItemSelected(item)

/*
        navController = findNavController(R.id.nav_host)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.findNavController()

        binding.bottomNav.setupWithNavController(navController)

         */

/*override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp()
}

 */
/*
    navController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
            R.id.profileFragment -> {
                binding.bottomNav.isVisible = false
            }
        }
    }

    binding.bottomNav.setOnItemSelectedListener { item ->
        when (item.itemId) {
            R.id.homeFragment -> {
            }
            R.id.floatingActionBtn -> {
            }
        }
        true
    }
    */

/*


 */
/*
 binding.bottomNav.apply {
     setupWithNavController(navController)
 }
  binding.floatingActionBtn.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Plant Doc")
            alertDialogBuilder.setMessage("Choose action:")
                .setPositiveButton(
                    "Camera",
                    DialogInterface.OnClickListener { dialog, which ->
                        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(camera, cameraRequestCode)
                    }
                )
                .setPositiveButton(
                    "Gallery",
                    DialogInterface.OnClickListener { dialog, which ->
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        startActivityForResult(intent, galleryRequestCode)
                    }
                )
                .setNegativeButton(
                    "Cancel",
                    DialogInterface.OnClickListener { dialog, which ->
                        dialog.cancel()
                    }
                )
            alertDialogBuilder.create().show()
        }
 */

 */

/*
navController.addOnDestinationChangedListener { _, destination, _ ->
    if (destination.id == R.id.loginFragment || destination.id == R.id.registerFragment || destination.id == R.id.dashboardFragment) {
        binding.bottomNav.visibility = View.GONE
    } else {
        binding.bottomNav.visibility = View.VISIBLE
    }
}

 */
