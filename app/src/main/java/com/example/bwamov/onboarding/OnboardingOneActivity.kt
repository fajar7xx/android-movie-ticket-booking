package com.example.bwamov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bwamov.R
import com.example.bwamov.auth.login.SignInActivity
import com.example.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.activity_onboarding_one.*

class OnboardingOneActivity : AppCompatActivity() {

//    inisialisasi
    lateinit var preference : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)
        System.getProperty("line.separator")

//        variable
        preference = Preferences(this)

        if(preference.getValues("onboarding").equals("1")){
            finishAffinity()
            startActivity(Intent(this, SignInActivity::class.java))
        }


//        membuat listener untuk button id
//        lanjut ke tampilan onboarding 2

        btn_lanjutkan.setOnClickListener{
//            var intent = Intent(this, OnboardingTwoActivity::class.java)
            val intentLanjutkan = Intent(this, OnboardingTwoActivity::class.java)
            startActivity(intentLanjutkan)
        }

//        menuju sign in activity
        btn_lewati.setOnClickListener{
            preference.setValues("onboarding", "1")

//            menghapus semua page sebelumnya sehingga tidak dapat di akses kembali
            finishAffinity()

//            var intent = Intent(this, SignInActivity::class.java)
            val intentLewati = Intent(this, SignInActivity::class.java)
            startActivity(intentLewati)
        }

    }
}