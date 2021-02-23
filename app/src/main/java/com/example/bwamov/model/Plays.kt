package com.example.bwamov.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//set jadi facable sehingga di bawa ke package mana aja enak
@Parcelize

data class Plays (
//    sesuaikan bentuk datanya
//    bisa null
    var nama : String ?= "",
    var url : String ?= ""
) : Parcelable