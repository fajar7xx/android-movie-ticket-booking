package com.example.bwamov.auth.register

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bwamov.home.HomeActivity
import com.example.bwamov.R
import com.example.bwamov.utils.Preferences
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_sign_up_photoscreen.*
import java.util.*

//silahkan extend listener untuk dexter agar dapat digunakan
class SignUpPhotoScreenActivity : AppCompatActivity(), PermissionListener {
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

        //TODO: error terjadi ketika upload image malah kembali ke halaman sebelumnya
        //TODO: cek proses upload image
        //todo: simpan data ke firebase berhasil tapi langsung back ke halaman sebelumnya

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


            }
        }

        btn_save.setOnClickListener {
//            artinya semua aktifity yang sudah dilakukan bakal di hapur
            finishAffinity()

            val goHome = Intent(this, HomeActivity::class.java)
            startActivity(goHome)
        }

//        upload ke firebase
        btn_save.setOnClickListener {
            if(filePath != null){

//                tampilkan dialog loading dengna progress dialog
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading......")
                progressDialog.show()

//                firebase job
//                upload nama file jadi uuid ke string
                var ref = storageReference.child("images/" + UUID.randomUUID().toString())
                ref.putFile(filePath)
                        .addOnSuccessListener {
//                            kalau sukses ini yang dulakukan
                            progressDialog.dismiss()
                            Toast.makeText(this, "Upload file berhasil", Toast.LENGTH_LONG).show()

//                            url
                            ref.downloadUrl.addOnSuccessListener {
                                preferences.setValues("url", it.toString())
                            }

//                            matikan semua aktifity yang ada lalu go to home
                            finishAffinity()
                            val goHome = Intent(this, HomeActivity::class.java)
                            startActivity(goHome)
                        }
                        .addOnFailureListener {
//                            kalau gagal ini yang dilakukan
                            progressDialog.dismiss()
                            Toast.makeText(this, "Upload file gagal", Toast.LENGTH_LONG).show()
                        }
                        .addOnProgressListener {
//                            memunculkan sudah berapa persen progressnya
                            taskSnapshot -> var progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                            progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                        }
            }else{

            }
        }


    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
//        kalau misalnya di setujui
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {}

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
        Toast.makeText(this, "File tidak bisa ditambahkan", Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        Toast.makeText(this, "hurry? silahkan lakukan upload nanti", Toast.LENGTH_LONG).show()
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            var bitmap = data?.extras?.get("data") as Bitmap
            statusAdd = true

            filePath = data.getData()!!
//            munculkan photo di profile
            Glide.with(this)
                    .load(bitmap)
                    .apply(RequestOptions.circleCropTransform())
                    .into(iv_profile)

//            setelah photo muncul dari photo profile
//            munculkan visibility tombol simpan
            btn_save.visibility = View.VISIBLE
//            ganti iv_image_add jadi btn_delete
            iv_add.setImageResource(R.drawable.ic_btn_delete)

        }
    }

}