package com.example.bwamov.auth.register

import android.Manifest
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bwamov.R
import com.example.bwamov.utils.Preferences
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import kotlinx.android.synthetic.main.activity_sign_up_photoscreen.*

class SignUpPhotoScreenActivity : AppCompatActivity() {
//    lateinit var digunakan untuk inisialisasi data awal

//    inisialisasi variable2 yang akan digunakan
    val REQUEST_IMAGE_CAPTURE = 1

//    kalau sudah menambahkan sebuah photo dia akan bernilai true
    var statusAdd : Boolean = false
    lateinit var filePath : Uri

//    jgn lupa atur storage pada firebase agar dapat digunakan
    lateinit var storage : FirebaseStorage
    lateinit var storageReference: StorageReference

    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photoscreen)
        System.getProperty("line.separator")

//        mencari photo terlebih dahulu menggunakan library dexter

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()

//        berikan data sebelumnya di signupactivity ke activity ini
        tv_hello.text = "Selamat Datang\n" + intent.getStringExtra("nama")

//        ketika tombol tambah photo di klik
//        sttus add menjadi false
//        lalu munculkan tombol save

        iv_add.setOnClickListener {
            if(statusAdd){
                statusAdd = false
                btn_save.visibility = View.VISIBLE

//                karena photonya udah dapat
//                iconnya di ubah donk
                iv_add.setImageResource(R.drawable.ic_btn_upload)
                iv_profile.setImageResource(R.drawable.user_pic)
            }else{
                Dexter.withActivity(this)
                    .withPermission(Manifest.permission.CAMERA)
                    .withListener(this)
                    .check()

//                lanjut besok di 15.30
            }
        }

    }
}