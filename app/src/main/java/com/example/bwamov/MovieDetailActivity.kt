package com.example.bwamov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.bwamov.checkout.PilihBangkuActivity
import com.example.bwamov.home.dashboard.PlaysAdapter
import com.example.bwamov.model.Film
import com.example.bwamov.model.Plays
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_pilih_bangku.*

class MovieDetailActivity : AppCompatActivity() {

//    inisialisasi
    private lateinit var mDatabase : DatabaseReference
    private var dataList = ArrayList<Plays>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

//        ambil data yang sudah dikirimkan tadi
//        dari dashboard fragment
        val data = intent.getParcelableExtra<Film>("data")

        //        inisialisasi
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")
            .child(data?.judul.toString())
            .child("play")

//        tampilkan pada textview yang sudah diberikan id
        tv_kursi.text = data?.judul
        tv_genre.text = data?.genre
        tv_desc.text = data?.desc
        tv_rate.text = data?.rating

//        load gambar
        Glide.with(this)
            .load(data?.poster)
            .into(iv_poster)

//        panggil datanya
        rv_who_played.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getData()

//        action pilih bangku
        btn_pilih_bangku.setOnClickListener {
            var intent = Intent(this, PilihBangkuActivity::class.java).putExtra("data", data)
            startActivity(intent)
        }
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
//                bersihkan dlu datanya biar gak ganggu
                dataList.clear()

                for(getDataSnapshot in snapshot.children){
                    var Film = getDataSnapshot.getValue(Plays::class.java)
                    dataList.add(Film!!)
                }

                rv_who_played.adapter = PlaysAdapter(dataList){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MovieDetailActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }

        })
    }

}