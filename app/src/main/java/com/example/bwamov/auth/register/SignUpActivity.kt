package com.example.bwamov.auth.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bwamov.R
import com.example.bwamov.auth.login.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

//    inisialisasi variable
    lateinit var setUsername : String
    lateinit var setPassword : String
    lateinit var setEmail : String
    lateinit var setName : String

//    inisialisasi firebase
    private lateinit var mFirebaseInstance : FirebaseDatabase
    private lateinit var mDatabase : DatabaseReference
    private lateinit var mFirebaseDatabaseReference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

//        inisialisasi valuenya
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mFirebaseDatabaseReference = mFirebaseInstance.getReference("User")

        btn_lanjutkan.setOnClickListener {
            setUsername = et_username.text.toString()
            setPassword = et_password.text.toString()
            setEmail = et_email.text.toString()
            setName = et_name.text.toString()

//            validasi data
            if(setUsername.equals("")){
                et_username.error = "Silahkan isikan username anda"
                et_username.requestFocus()
            }else if(setPassword.equals("")){
                et_password.error = "Silahkan isikan password anda"
                et_password.requestFocus()
            }else if(setEmail.equals("")){
                et_email.error = "Silahkan isikan email anda"
                et_email.requestFocus()
            }else if(setName.equals("")){
                et_name.error = "Silahkan isikan nama anda"
                et_name.requestFocus()
            }else{
//                jika semua nya gak ada error, simpan data ke firebase
                saveUsername(setUsername, setPassword, setEmail, setName)
            }
        }
    }

    private fun saveUsername(username: String, password: String, email: String, name: String) {
//        tampung datanya terlebih dahulu
        var user = User()
        user.username = setUsername
        user.password = setPassword
        user.email = setEmail
        user.nama = setName

//        check apakah username sudah digunakan atau belum
        if(setUsername != null){
            checkingUsername(setUsername, user)
        }
    }

    private fun checkingUsername(username: String, data: User) {
//        terapkan firebase disini
        mFirebaseDatabaseReference.child(setUsername).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
//                lakukan pengecekan data seperti di login activity
                var user = snapshot.getValue(User::class.java)

                if(user == null){
//                    buat akun
                    mFirebaseDatabaseReference.child(setUsername).setValue(data)

//                    ke laman signup photo screen sekalian membawa data yang akan di proses
                    val goSignUpPhotoScreen = Intent(this@SignUpActivity, SignUpPhotoScreenActivity::class.java).putExtra("nama", data.nama)
                    startActivity(goSignUpPhotoScreen)
                }else{
//                    error
                    Toast.makeText(this@SignUpActivity, "User sudah digunakan",
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpActivity, ""+error.message,
                    Toast.LENGTH_LONG).show()
            }

        })
    }
}