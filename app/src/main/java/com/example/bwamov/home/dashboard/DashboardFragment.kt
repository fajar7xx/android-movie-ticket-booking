package com.example.bwamov.home.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bwamov.MovieDetailActivity
import com.example.bwamov.R
import com.example.bwamov.model.Film
import com.example.bwamov.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {

    // parameter data sementara
    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference

    private var dataList = ArrayList<Film>()
//    Film itu model ya
//    buat model untuk variabel diatas untuk menghandle datanya

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

//    agar tidak menginisiasi view di dalamnya
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        buat inisiasi
        preferences = Preferences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        tv_nama.setText(preferences.getValues("nama"))
        if(preferences.getValues("saldo").equals("")){
            currency(preferences.getValues("saldo")!!.toDouble(), tv_saldo)
        }

//        library glide untuk menampilkan gambar
        Glide.with(this)
                .load(preferences.getValues("url"))
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)

//        siapkan recyvle view
        rv_now_playing.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_coming_soon.layoutManager = LinearLayoutManager(context)

//        panggil datanya
        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

//                clear datanya biar tidak ada data duplikatnya
                dataList.clear()
                for (getdataSnapshot in snapshot.children){
//                    tampung valuenya
                    var film = getdataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

//                set adapter
                rv_now_playing.adapter = NowPlayingAdapter(dataList){
//                    kalau di tekan akan intent ke bagian detail
//                    mengirim data pada adapter ini ke detail activiry
                    var intent = Intent(context, MovieDetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }
//                set adapter
                rv_coming_soon.adapter = ComingSoonAdapter(dataList){
//                     kalau di tekan akan intent ke bagian detail
//                     mengirim data pada adapter ini ke detail activiry
                    var intent = Intent(context, MovieDetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
//                pakai contect karena ini fragment
                Toast.makeText(context, "" + databaseError.message, Toast.LENGTH_LONG)
            }

        })
    }

    //    fungsi konvert format duit
    private fun currency(harga: Double, textView : TextView){
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        textView.setText(format.format(harga))
    }
}