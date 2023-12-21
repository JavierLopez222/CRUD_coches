package com.example.crud_coches

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coche(
    var id: String? = null,
    var marca :String? = null,
    var modelo : String? = null,
    var matricula :String? = null,
):Parcelable
