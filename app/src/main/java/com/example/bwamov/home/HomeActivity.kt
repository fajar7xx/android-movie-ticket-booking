package com.example.bwamov.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.bwamov.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        set yang pertamam muncul dashboard
        val fragmentHome = DashboardFragment()
        val fragmentTiket = TiketFragment()
        val fragmentSetting = SettingFragment()


//        panggil fragmentnya
        setFragment(fragmentHome)

//        menu home
        iv_menu1.setOnClickListener {
            setFragment(fragmentHome)

//            ganti icon menjadi aktif
            changeIcon(iv_menu1, R.drawable.ic_home_active)
            changeIcon(iv_menu2, R.drawable.ic_tiket)
            changeIcon(iv_menu3, R.drawable.ic_profile)
        }

//        menu tiket
        iv_menu2.setOnClickListener {
//            ketika klik menu ini maka fragmentnya wajib di ubah
            setFragment(fragmentTiket)

//            ganti icon menjadi aktif
            changeIcon(iv_menu1, R.drawable.ic_home)
            changeIcon(iv_menu2, R.drawable.ic_tiket_active)
            changeIcon(iv_menu3, R.drawable.ic_profile)
        }

//        menu setting
        iv_menu3.setOnClickListener {
//            ganti fragment ketika masuk menu baru
            setFragment(fragmentSetting)

//            ganti icon menjadi aktif
            changeIcon(iv_menu1, R.drawable.ic_home)
            changeIcon(iv_menu2, R.drawable.ic_tiket)
            changeIcon(iv_menu3, R.drawable.ic_profile_active)
        }

    }

//    melakukan load pada fragment
    private fun setFragment(fragment: Fragment){
        val fragmentManager= supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

//        load fragment, dan fragment yang kita buka
        fragmentTransaction.replace(R.id.layout_frame, fragment)

//        commitkan
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView: ImageView, int: Int){
        //integer
        imageView.setImageResource(int)
    }
}