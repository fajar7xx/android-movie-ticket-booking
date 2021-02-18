package com.example.bwamov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bwamov.R
import kotlinx.android.synthetic.main.activity_onboarding_two.*

class OnboardingTwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_two)
        System.getProperty("line.separator")

//        button untuk lewati perkenalan
//        cara lain menggunakan intent
        btn_lanjutkan.setOnClickListener {

//            menghapus semua page sebelumnya sehingga tidak dapat di akses kembali
            finishAffinity()

            startActivity(Intent(this, OnboardingThreeActivity::class.java))
        }
    }
}