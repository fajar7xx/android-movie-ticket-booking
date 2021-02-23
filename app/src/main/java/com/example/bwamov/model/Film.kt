package com.example.bwamov.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//set jadi facable sehingga di bawa ke package mana aja enak
@Parcelize

data class Film (
//    sesuaikan bentuk datanya
//    bisa null
    var desc : String ?= "",
    var director : String ?= "",
    var genre : String ?= "",
    var judul : String ?= "",
    var rating : String ?= "",
    var poster : String ?= ""
) : Parcelable