package com.alexmumo.plantdoc.ui.fragments.splash

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SplashViewModel: ViewModel() {
    var splash: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    fun setUp() {
        Handler().postDelayed({
            splash.value = FirebaseAuth.getInstance().currentUser == null
        }, 3000)
    }
}
