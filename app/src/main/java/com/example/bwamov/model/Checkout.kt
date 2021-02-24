package com.example.bwamov.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//set jadi facable sehingga di bawa ke package mana aja enak
@Parcelize

data class Checkout (
//    sesuaikan bentuk datanya
//    bisa null
    var kursi : String ?= "",
    var harga : String ?= ""
) : Parcelable