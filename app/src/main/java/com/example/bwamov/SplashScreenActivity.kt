package com.example.bwamov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.bwamov.onboarding.OnboardingOneActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        variable
        var handler = Handler()

//        set delay selama 5 detik
//        menjalankan inten splash screen
//        ke onboarding satu
        handler.postDelayed({
//            dari splash screen ke onboarding one activity
            var intent = Intent(this@SplashScreenActivity, OnboardingOneActivity::class.java)
            startActivity(intent)

//            untuk page ini harus di destroy ketika berpindah
//            dari page splash ke page onboarding
            finish()

        }, 3000)
    }
}