package com.alexmumo.plantdoc.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import com.alexmumo.plantdoc.R
import com.alexmumo.plantdoc.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val cameraRequestCode = 1
    private val galleryRequestCode = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        // splash screen
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.background = null
        binding.bottomNav.menu.getItem(2).isEnabled = false

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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeFragment -> {
                Toast.makeText(this, "Home Fragment Clicked", Toast.LENGTH_LONG).show()
            }
            R.id.profileFragment -> {
                Toast.makeText(this, "Home Fragment Clicked", Toast.LENGTH_LONG).show()
            }
            R.id.mapsFragment -> {
                Toast.makeText(this, "Home Fragment Clicked", Toast.LENGTH_LONG).show()
            }
            R.id.settingsFragment -> {
                Toast.makeText(this, "Home Fragment Clicked", Toast.LENGTH_LONG).show()
            }
        }
        return true
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
*/
