package com.example.bwamov.utils

import android.content.Context
import android.content.SharedPreferences

class Preferences(val context: Context) {
//    buat objek terlebih dahulu
    companion object{
        const val USER_PREFF = "USER_PREFF"
    }

//    function untuk membuat shared preferences
    var sharedPreferences = context.getSharedPreferences(USER_PREFF, 0)

//    variable untuk set value dan ambil value
    fun setValues(key : String, value : String){
//        perizinan untuk apakah boleh menambah data disini atau tidak
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

//    maksudnya datanya string lalu returnnya string setelah parameter itu
//    bentuk balikan datanya
    fun getValues(key : String) : String?{
//        kembalikan datanya key kalau gak ada kita kembalikan string
        return sharedPreferences.getString(key, "")
    }


}