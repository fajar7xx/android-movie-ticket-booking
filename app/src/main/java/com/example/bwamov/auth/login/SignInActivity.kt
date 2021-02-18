package com.example.bwamov.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bwamov.HomeActivity
import com.example.bwamov.R
import com.example.bwamov.auth.register.SignUpActivity
import com.example.bwamov.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

//    deskripsi variable dan tipe datanya
    lateinit var iUsername:String
    lateinit var iPass:String

//    inisialisasi dari firebase
    lateinit var mDatabase:DatabaseReference

//    inisialisasi lalu ambil class nya dari utils.Preferences
    lateinit var preference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

//        value database firebase
//        ke table user
        mDatabase = FirebaseDatabase.getInstance().getReference("User")
//        inisialisai variable
        preference = Preferences(this)

//        set value untuk onboarding jadi ketika sudah next sampai khatam
//        maka tidak akan keluar lagi
//        cukup untuk running aplikasi pertama kali
        preference.setValues("onboarding", "1")

        if(preference.getValues("status").equals("1")){
//            kalau status 1 maka gak usah login
//            masuk aja ke home activity

//            hapus semua aktifity yang sudah tampil
            finishAffinity()
            val goHomeAct = Intent(this, HomeActivity::class.java)
            startActivity(goHomeAct)
        }


        btn_masuk.setOnClickListener {
            //just test a database to firebase
            // Write a message to the database
            // Write a message to the database
//            val database = FirebaseDatabase.getInstance()
//            val myRef = database.getReference("message")
//
//            myRef.setValue("Hello, World!")


//            buat value terlebih dahulu untuk menampung nilai inputan
            iUsername = et_username.text.toString()
            iPass = et_password.text.toString()

//            tampilkan error kalau username kosong
            if(iUsername.equals("")){
                et_username.error = "Silahkan masukkan username anda"

//                setelah tampil error fokuskan error ke yang di tuju
                et_username.requestFocus()
            }else if(iPass.equals("")){
                et_password.error = "Silahkan masukkan password anda"
//                tampilkan error
                et_password.requestFocus()
            }else{
//                no error langsung ke function pushLogin untuk di proses
                pushLogin(iUsername, iPass)
            }
        }

        btn_daftar.setOnClickListener {
            val intentDaftar = Intent(this, SignUpActivity::class.java)
            startActivity(intentDaftar)
        }
    }

//    function untuk memproses login
    private fun pushLogin(iUsername: String, iPass: String) {
//        pengecekan username
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
//                class model untuk menangani data user
                var user = snapshot.getValue(User::class.java)

//                cek apakah data username ada atau tidak
                if(user==null){
                    Toast.makeText(this@SignInActivity,
                        "User tidak ditemukan",
                        Toast.LENGTH_LONG).show()
                }else{
//                    cek dlu apakah password nya sama dan sesuai dengan yang di database
                    if(user.password.equals(iPass)){
//                        kalau sukses set datanya
//                        simpan beberapa value
                        preference.setValues("nama", user.nama.toString())
                        preference.setValues("username", user.username.toString())
                        preference.setValues("url", user.url.toString())
                        preference.setValues("email", user.email.toString())
                        preference.setValues("saldo", user.saldo.toString())
//                        1 artinya login, 0 artinya belom login/penyusup ini
                        preference.setValues("status", "1")

//                        kalau username di temukan lempar data ke home
                        var intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@SignInActivity,
                            "Password anda salah",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignInActivity,
                    error.message,
                    Toast.LENGTH_LONG).show()
            }
        })
    }
}