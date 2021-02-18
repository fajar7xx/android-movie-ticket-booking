package com.example.bwamov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bwamov.R
import com.example.bwamov.auth.login.SignInActivity
import kotlinx.android.synthetic.main.activity_onboarding_three.*

class OnboardingThreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_three)
        System.getProperty("line.separator")

        btn_mulai.setOnClickListener {

//            menghapus semua page sebelumnya sehingga tidak dapat di akses kembali
            finishAffinity()

//            var intentMulai = Intent(this, SignInActivity::class.java)
            val intentMulai = Intent(this, SignInActivity::class.java)
            startActivity(intentMulai)
        }
    }
}